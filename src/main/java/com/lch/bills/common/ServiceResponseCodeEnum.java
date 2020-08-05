package com.lch.bills.common;

public enum ServiceResponseCodeEnum {

    /**
     * 公共系统返回码 （第四位是0）
     */
    SYS_SUCCESS("000000", "成功"),
    SYS_FAILED("001000", "失败"),
    SYS_PARAM_NOT_RIGHT("001001", "请求数据校验失败"),
    QUERY_DATA_NOT_EXIST("001002", "查询数据不存在"),
    STATUS_NOT_RIGHT("001003", "数据状态校验不通过"),
    REQUEST_DATA_NOT_EXIST("001004", "请求提交的数据不存在"),
    USER_NOT_LOGIN("001006","用户未登录"),
    USER_OR_PASSWORD_ERROR("001007","用户不存在或帐号密码错误"),
    ACCESS_LIMITER("001008","访问被限制"),
    DATA_SAVE_ERROR("001009","数据保存失败"),
    USER_LOSE_EFFICACY("001010","登陆失效"),
    USER_EXITS("001011","用户存在"),
    USER_ADD_FAILED("001012","用户添加失败"),
    USER_UPDATE_FAILED("001013","用户编辑失败"),
    SEND_SMS_FAILED("001014","短信发送失败"),
    CHECK_SMS_FAILED("001016","短信验证失败"),
    SEND_SMS_FAILED_FREQUENTLY("001015","短信发送频繁，请稍后再试"),
    RESET_PASSWORD_FAILED("001017","重置密码失败"),
    AUDIT_REGISTER_FAILED("001018","注册审核不通过"),
    IMPROVE_PERSON_INFO("001019","完善个人信息"),
    SYSTEM_BUSY("001099", "系统繁忙,请稍后重试"),
    MONITOR_FILE_ADD("002001", "添加了文件"),
    MONITOR_FILE_DEL("002002", "删除了文件"),
    UNARRIVE_ANY_TASK("002003", "没有到达任何作业现场"),
    ARRIVE_ILLEGAL_TASK("002004", "到达未指定的作业现场");

    private final String code;
    private final String msg;

    ServiceResponseCodeEnum(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
