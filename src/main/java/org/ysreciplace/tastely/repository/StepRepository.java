package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ysreciplace.tastely.entity.Step;

@Mapper
public interface StepRepository {
    public int stepSave(Step step);
}
