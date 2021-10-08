//package com.guet.coal_trading_platform.service;
//
//import com.kexie.academic_early_warning.dto.UserPasswordObject;
//import com.kexie.academic_early_warning.dto.UserPersonalInfo;
//import com.kexie.academic_early_warning.mbg.model.AAuth;
//import com.kexie.academic_early_warning.mbg.model.AUser;
//import org.springframework.security.core.Authentication;
//
//import java.util.List;
//
///**
// * Created by 欲隐君。 on 2021/3/15
// */
//
//
//public interface UserService {
//
//    /**
//      *
//      * @author HuangJinTai
//      * @date 2021/3/17 17:31
//      * @param account: 用户账号
//      * @return: user对象
//      *
//      */
//    AUser getUserByAccount(String account);
//
//    /**
//      *
//      * @author HuangJinTai
//      * @date 2021/3/17 17:32
//      * @param account: 账号
//      * @param password: 密码
//      * @return: token
//      *
//      */
//    String login(String account,String password);
//
//
//    /**
//      *
//      * @author HuangJinTai
//      * @date 2021/3/17 17:32
//      * @param account:用户账号
//      * @return: 权限列表
//      *
//      */
//    List<AAuth> getaAuthList(String account);
//
//    UserPersonalInfo getUserPersonalInfo(Authentication authentication);
//
//    /**
//     * 更新当前用户的基本信息（此项拒绝修改密码等相关服务）
//     * @param aUser 用户信息
//     * @return
//     */
//    boolean updateAUserByPrimaryKey(AUser aUser);
//
//    /**
//     *
//     * @param authentication 当前用户
//     * @param passwordObject 密码对象（包含新密码与旧密码）
//     * @return
//     */
//    boolean updateAUserPassowordByPrimaryKey(Authentication authentication, UserPasswordObject passwordObject);
//
//}
