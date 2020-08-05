package com.lch.bills.repo;


import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.EveryDayBills;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface BillsRepo {

    /**
     * 添加用户账单
     *
     * @param bills
     * @return
     */
    int save(Bills bills);

    /**
     * 删除账单
     *
     * @param id
     * @param userId
     * @return
     */
    @Delete("delete from t_bills where id =#{id} and userId =#{userId}")
    int delBills(Long id, Long userId);

    /**
     * 查询用户的所有账单
     *
     * @param userId
     * @return
     */
    List<EveryDayBills> getBillsList(Date createDate, Long userId);

}
