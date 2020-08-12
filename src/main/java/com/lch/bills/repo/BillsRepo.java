package com.lch.bills.repo;


import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.ChartData;
import com.lch.bills.pojo.vo.EveryDayBills;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    Integer save(Bills bills);

    /**
     * 删除账单
     *
     * @param id
     * @param userId
     * @return
     */
    @Delete("delete from t_bills where id =#{id} and userId =#{userId}")
    Integer delBills(Long id, Long userId);

    /**
     * 查询用户的所有账单
     *
     * @param userId
     * @return
     */
    List<EveryDayBills> getBillsList(Date createDate, Long userId);


    Bills getBills(Long id);

    Integer editBills(Bills bills);

    /**
     * 通过年份查询每个月的数据
     *
     * @param year
     * @param type
     * @param userId
     * @return
     */
    List<ChartData> everyYearData(Integer year, Integer type, Long userId);

    /**
     * 通过时间查询该月的每一天的数据
     *
     * @param createDate
     * @param type
     * @param userId
     * @return
     */
    List<ChartData> everyMonthData(Date createDate, Integer type, Long userId);

    /**
     * 通过时间，查询该时间后七天的数据
     *
     * @param minDate
     * @param type
     * @param userId
     * @return
     */
    List<ChartData> everyWeekData(String minDate, Integer type, Long userId);

    @Select("select min(createDate) as minDate from t_bills where userId = #{userId}")
    Bills getBillsMinDate(Long userId);

    @Select("select max(createDate) as maxDate from t_bills where userId = #{userId}")
    Bills getBillsMaxDate(Long userId);

    List<Bills> chartsDataDetail(String startDate, String endDate, Integer type, Long userId);


    List<Bills> getBillsByRemakeAndTypeId(String startDate, String endDate, Integer type, Long userId,String remake,Long userBillsTypeId);


    List<Bills> getAllBillsList(Long userId);

    //删除类型时删除对应的所有账单
    @Delete("delete from t_bills where userBillsTypeId=#{userBillsTypeId} and userId=#{userId}")
    Integer delBillsByTypeId(Long userBillsTypeId,Long userId);
}
