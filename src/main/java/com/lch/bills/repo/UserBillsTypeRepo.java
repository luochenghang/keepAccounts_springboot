package com.lch.bills.repo;


import com.lch.bills.pojo.BillsType;
import com.lch.bills.pojo.UserBillsType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserBillsTypeRepo {

    /**
     * 添加用户账单类型
     *
     * @param userBillsType
     * @return
     */
    Integer save(UserBillsType userBillsType);

    /**
     * 删除账单类型
     *
     * @param id
     * @param userId
     * @return
     */
    @Delete("delete from t_user_bills_type where id =#{id} and userId =#{userId}")
    int delBillsType(Long id, Long userId);

    /**
     * 查询用户的所有账单类型
     *
     * @param userId
     * @return
     */
    List<UserBillsType> getUserBillsTypeList(Long userId, Integer type);

    /**
     * 账单类型排序
     *
     * @param id
     * @param userId
     * @param beforeId 前一个账单类型的id
     * @return
     */
    int sortUserBillsType(Long id, Long userId, Long beforeId);


    @Select("select * from t_bills_type where type != 0")
    List<BillsType> getInitBillsType();

    @Select("select * from t_bills_type where type = 0")
    List<BillsType> getSystemBillsTypeList();

    @Select("select * from t_bills_type where id = #{id}")
    BillsType getSystemBills(Long id);
}
