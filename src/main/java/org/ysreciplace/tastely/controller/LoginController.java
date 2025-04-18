package org.ysreciplace.tastely.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.entity.Verification;
import org.ysreciplace.tastely.repository.UserRepository;
import org.ysreciplace.tastely.repository.VerificationRepository;
import org.ysreciplace.tastely.request.LoginRequest;
import org.ysreciplace.tastely.service.KakaoApiService;
import org.ysreciplace.tastely.service.MailSendService;
import org.ysreciplace.tastely.service.NaverApiService;
import org.ysreciplace.tastely.vo.KakaoTokenResponse;
import org.ysreciplace.tastely.vo.NaverProfileResponse;
import org.ysreciplace.tastely.vo.NaverTokenResponse;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class LoginController {

    private final VerificationRepository verificationRepository;
    private final KakaoApiService kakaoApiService;
    private final NaverApiService naverApiService;
    private MailSendService mailSendService;
    private UserRepository userRepository;


    @GetMapping("/login")
    public String loginHandle(Model model) {

        model.addAttribute("kakaoClientId", "1fc2e7802aaca68e3e5bdd3462557b09");
        model.addAttribute("kakaoRedirectUri", "http://127.0.0.1:8080/auth/kakao/callback");

        model.addAttribute("naverClientId", "NlUQDGh2LlNagycoFLWV");
        model.addAttribute("naverRedirectUri", "http://127.0.0.1:8080/auth/naver/callback");

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginPostHandle(@RequestParam("user-id") String userId,
                                  @RequestParam("password") String password,
                                  HttpSession session) {
        User user = userRepository.findByUsernameOrEmail(userId);
        if (user == null || !user.getPassword().equals(password)){
            return "redirect:/auth/login";
        } else {
            session.setAttribute("user", user);
            return "redirect:/ranking";
        }
    }

    @GetMapping("/signup")
    public String signupGetHandle(Model model) {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupPostHandle(@ModelAttribute User user) {
        User found = userRepository.findByUsernameOrEmail(user.getEmail());

        if (found == null) {
            user.setProvider("LOCAL");
            user.setVerified("F");
            userRepository.save(user);
            mailSendService.sendWelcomeMessage(user);
        }
        return "redirect:/ranking";
    }

    @GetMapping("/logout")
    public String logoutHandle(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/email-check")
    public String emailCheck() {
        return "auth/email-check";
    }

    @PostMapping("/email-create-verification")
    public String emailCheckPostHandle(@RequestParam("email") String email, Model model) {

        User user = userRepository.findByUsernameOrEmail(email);
        if (user == null) {
            model.addAttribute("error" + "이메일이 틀립니다.");
            return "redirect:/auth/login";
        }

        String result = UUID.randomUUID().toString();

        Verification verification = new Verification();
        verification.setToken(result);
        verification.setUserEmail(user.getEmail());

        verificationRepository.create(verification);

        mailSendService.sendEmailVerify(verification.getUserEmail(), verification.getToken());

        return "redirect:/auth/login";
    }

    //============================카카오톡===================================

    @GetMapping("/kakao/callback")
    public String kakaoCallbackHandle(@RequestParam("code") String code,
                                      HttpSession session) throws JsonProcessingException {

        KakaoTokenResponse response = kakaoApiService.exchangeToken(code);

        DecodedJWT jwt = JWT.decode(response.getIdToken());

        String sub = jwt.getClaim("sub").asString();
        String nickname = jwt.getClaim("nickname").asString();

        User found = userRepository.findByProviderAndProviderId("KAKAO", sub);
        if (found != null) {
            session.setAttribute("user", found);
        }else {
            User user = User.builder().provider("KAKAO").providerId(sub).nickname(nickname).verified("T").build();
            userRepository.save(user);
            session.setAttribute("user", user);
        }
        log.info("jwt: sub={}, nickname={}", sub, nickname);
        return "redirect:/ranking";
    }


    //==================NAVER==============================================================

    @GetMapping("/naver/callback")
    public String naverCallbackHandle(@RequestParam("code") String code,
                                      @RequestParam("state") String state,
                                      HttpSession session) throws JsonProcessingException {

        log.info("code={}, state={}", code, state);

        NaverTokenResponse tokenResponse =
                naverApiService.exchangeToken(code, state);

        log.info("accessToken={}", tokenResponse.getAccessToken());

        NaverProfileResponse profileResponse =
                naverApiService.exchangeProfile(tokenResponse.getAccessToken());

        log.info("profileResponse id={}", profileResponse.getId());
        log.info("profileResponse nickname={}", profileResponse.getNickname());
        log.info("profileResponse profileImage={}", profileResponse.getProfileImage());

        User found = userRepository.findByProviderAndProviderId("NAVER", profileResponse.getId());
        if (found == null) {
            User user = User.builder()
                    .nickname(profileResponse.getNickname())
                    .provider("NAVER")
                    .providerId(profileResponse.getId())
                    .verified("T")
                    .build();

            userRepository.save(user);
            session.setAttribute("user", user);
        }else {
            System.out.println("로그인 완료");
            session.setAttribute("user", found);
        }
        return "redirect:/ranking";
    }

}

