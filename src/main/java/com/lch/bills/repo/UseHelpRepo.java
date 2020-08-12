package com.lch.bills.repo;


import com.lch.bills.pojo.UseHelp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UseHelpRepo {


    @Select("select * from t_use_help order by sort")
    List<UseHelp> getUseHelp();
}
