package com.lch.bills.jwt;



import com.lch.bills.common.ServiceResponseCodeEnum;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * jwt加密和解密的工具类
 */
public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static final String JWT_KEY_USER_ID = "uid";
    public static final int expire = 172800;

    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] dc = DatatypeConverter.parseBase64Binary("xiaoneng_alertor_service");
        return new SecretKeySpec(dc, signatureAlgorithm.getJcaName());
    }

    //生成token的方法  适用于登录
    public static String generatorToken(JwtInfo jwtInfo) {

        return Jwts.builder().claim(JWT_KEY_USER_ID, jwtInfo.getUid())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }


    //根据token获得token中的信息
    public static JwtInfo getTokenInfo(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return new JwtInfo((claims.get(JWT_KEY_USER_ID)).toString());
    }


    /**
     * 验证JWT
     *
     * @param token
     * @return
     */
    public static CheckResult validateJWT(String token) {
        CheckResult checkResult = new CheckResult();

        try {
            JwtInfo jwtInfo = JwtUtils.getTokenInfo(token);
            checkResult.setUid(jwtInfo.getUid());
            checkResult.setCode(ServiceResponseCodeEnum.SYS_SUCCESS.getCode());
            checkResult.setMsg(ServiceResponseCodeEnum.SYS_SUCCESS.getMsg());
            checkResult.setSuccess(true);
        } catch (ExpiredJwtException e) {
            //TODO  超时处理
            logger.error("error", e);
            checkResult.setCode(ServiceResponseCodeEnum.USER_LOSE_EFFICACY.getCode());
            checkResult.setMsg(ServiceResponseCodeEnum.USER_LOSE_EFFICACY.getMsg());
        } catch (SignatureException e) {
            logger.error("error", e);
            checkResult.setCode(ServiceResponseCodeEnum.USER_LOSE_EFFICACY.getCode());
            checkResult.setMsg(ServiceResponseCodeEnum.USER_LOSE_EFFICACY.getMsg());
        } catch (Exception e) {
            checkResult.setCode(ServiceResponseCodeEnum.SYS_FAILED.getCode());
            checkResult.setMsg(ServiceResponseCodeEnum.SYS_FAILED.getMsg());
        } finally {
            logger.info("checkAuth response: {}", checkResult);
        }
        return checkResult;

    }

}
