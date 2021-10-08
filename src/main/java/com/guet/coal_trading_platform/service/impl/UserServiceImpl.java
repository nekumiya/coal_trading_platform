//package com.guet.coal_trading_platform.service.impl;
//
//import com.kexie.academic_early_warning.common.utils.JwtTokenUtil;
//import com.kexie.academic_early_warning.common.utils.RSAUtils;
//import com.kexie.academic_early_warning.dao.UserRoleRelationDao;
//import com.kexie.academic_early_warning.dto.UserPasswordObject;
//import com.kexie.academic_early_warning.dto.UserPersonalInfo;
//import com.kexie.academic_early_warning.mbg.mapper.AUserMapper;
//import com.kexie.academic_early_warning.mbg.model.AAuth;
//import com.kexie.academic_early_warning.mbg.model.AMajor;
//import com.kexie.academic_early_warning.mbg.model.AUser;
//import com.kexie.academic_early_warning.mbg.model.AUserExample;
//import com.kexie.academic_early_warning.service.AdminCacheService;
//import com.kexie.academic_early_warning.service.MajorService;
//import com.kexie.academic_early_warning.service.UserService;
//import org.apache.commons.collections4.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// *
// * 系统用户Service实现类
// * Created by 欲隐君。 on 2021/3/15
// */
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(com.kexie.academic_early_warning.service.impl.UserServiceImpl.class);
//
//
//    private AdminCacheService adminCacheService;
//
//    private UserDetailsService userDetailsService;
//
//    private JwtTokenUtil jwtTokenUtil;
//    private PasswordEncoder passwordEncoder;
//    private String tokenHead;
//    private String privateKey;
//    private AUserMapper userMapper;
//    private UserRoleRelationDao userRoleRelationDao;
//    private MajorService majorService;
//    private RSAUtils rsaUtils;
//
//
//
//    @Autowired
//    public UserServiceImpl(AdminCacheService adminCacheService,
//                           UserDetailsService userDetailsService,
//                           JwtTokenUtil jwtTokenUtil,
//                           PasswordEncoder passwordEncoder,
//                           @Value("${jwt.tokenHead}") String tokenHead,
//                           @Value("${rsa.privateKey}") String privateKey,
//                           AUserMapper userMapper,
//                           UserRoleRelationDao userRoleRelationDao,
//                           MajorService majorService, RSAUtils rsaUtils) {
//        this.adminCacheService = adminCacheService;
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.passwordEncoder = passwordEncoder;
//        this.tokenHead = tokenHead;
//        this.privateKey = privateKey;
//        this.userMapper = userMapper;
//        this.userRoleRelationDao = userRoleRelationDao;
//        this.majorService = majorService;
//        this.rsaUtils = rsaUtils;
//    }
//
//
//    /**
//     * 获取用户的信息 启用缓存获取
//     * @param account: 用户账号
//     * @return
//     */
//    @Override
//    public AUser getUserByAccount(String account) {
//        //先去缓存里面找，看有没有对应的用户信息
//        AUser user = adminCacheService.getAUser(account);
//        if (user != null) return user;
//
//        AUserExample example = new AUserExample();
//        example.createCriteria().andUserAccountEqualTo(account);
//        List<AUser> userList = userMapper.selectByExample(example);
//        if (userList != null && userList.size()>0) {
//            AUser user1 = userList.get(0);
//            //将数据库中数据存到缓存中
//            adminCacheService.setAUser(user1);
//            return user1;
//        }
//        return null;
//    }
//
//    /**
//      * 更新上次登录时间
//      * @author HuangJinTai
//      * @date 2021/3/17 20:41
//      * @param account:
//      * @return: 0 -> 更新失败  ， 1 -> 更新成功
//      *
//      */
//    public Integer updateLastLoginTime(String account){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        AUser aUser = new AUser();
//        aUser.setUserAccount(account);
//        aUser.setUserLastLogIn(simpleDateFormat.format(new Date()));
//        return userMapper.updateByPrimaryKeySelective(aUser);
//    }
//
//
//    @Override
//    public String login(String account, String password) {
//        String token = null;
//        try {
////            //启用RSA时去掉注释即可
////            password=RSAUtils.privateDecrypt(password, privateKey);
////            account=RSAUtils.privateDecrypt(account,privateKey);
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(account);
//
//            //数据库密码加密后，再使用passwordEncoder进行验证
////            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
////                throw new BadCredentialsException("密码不正确");
////            }
//
//            if(!password.equals(userDetails.getPassword())){
//                throw new BadCredentialsException("密码错误！！！");
//            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            token = jwtTokenUtil.generateToken(userDetails);
//            //updateLastLoginTime(account);
//
//        } catch (AuthenticationException e) {
//            LOGGER.warn("登录异常:{}", e.getMessage());
//        }catch (RuntimeException e){
//            LOGGER.warn("解密异常:{}",e.getMessage());
//        }
//        return token;
//    }
//
//    @Override
//    public List<AAuth> getaAuthList(String account) {
//        //先去缓存中寻找有没有对应的用户权限
//        List<AAuth> auths = adminCacheService.getAllAuth(account);
//        if (CollectionUtils.isNotEmpty(auths)){
//            return auths;
//        }else {
//            List<AAuth> list = userRoleRelationDao.getAuthList(account);
//            if (CollectionUtils.isNotEmpty(list)) adminCacheService.setAllAuth(account,list);
//            return list;
//        }
//    }
//
//    @Override
//    public UserPersonalInfo getUserPersonalInfo(Authentication authentication) {
//        UserPersonalInfo userPersonalInfo=new UserPersonalInfo();
//        LOGGER.info(authentication.getAuthorities()+"");
//        userPersonalInfo.setAuth( authentication.getAuthorities().stream().map(s->s+"").filter(s -> s.startsWith("sys")).collect(Collectors.toList()));
//        AUser aUser = getUserByAccount(authentication.getName());
//        AMajor major=null;
//        if (Optional.ofNullable(aUser.getMajorId()).isPresent()){
//            major = majorService.getMajorByMajorId(aUser.getMajorId());
//        }
//        userPersonalInfo.setEmail(aUser.getUserEmail());
//        userPersonalInfo.setMajorName(Optional.ofNullable(major).isPresent()?major.getMajorName():"暂无");
//        userPersonalInfo.setUserPhone(aUser.getUserPhone());
//        userPersonalInfo.setLastLoginTime(aUser.getUserLastLogIn());
//        userPersonalInfo.setUserAccount(authentication.getName());
//        userPersonalInfo.setUserName(aUser.getUserName());
//        LOGGER.info(userPersonalInfo.toString());
//        return userPersonalInfo;
//    }
//
//    @Override
//    public boolean updateAUserByPrimaryKey(AUser aUser)  {
//        if (aUser == null) return false;
//        //以下数据对用户个人信息修改不允许更新
//        aUser.setMajorId(null);
//        aUser.setUserPassword(null);
//        aUser.setIsDeleted(null);
//        aUser.setUserCrteateTime(null);
//        aUser.setUserLastLogIn(null);
//        boolean flag=true;
//        int i=0;
//        try {
//            i = userMapper.updateByPrimaryKeySelective(aUser);
//
//        }catch (Exception e){
//            LOGGER.info(e.getMessage());
//            flag=false;
//        }
//
//        if (i == 0 ) flag=false;
//        if (flag) adminCacheService.delAdmin(aUser.getUserAccount());
//        return flag;
//    }
//
//    @Override
//    public boolean updateAUserPassowordByPrimaryKey(Authentication authentication, UserPasswordObject passwordObject) {
//
//        /**
//         * 对用户的账号密码(RSA)解密
//         */
////        String oldPassword = RSAUtils.privateDecrypt(passwordObject.getOldPassword(), privateKey);
////        String newPassword = RSAUtils.privateDecrypt(passwordObject.getNewPassword(), privateKey);
//
//        boolean flag=false;
//        AUser aUser = new AUser();
//        aUser.setUserAccount(authentication.getName());
//        AUser user = userMapper.selectByPrimaryKey(authentication.getName());
//
//        String userPassword =user.getUserPassword();
//        /**
//         * 以下内容调试阶段开启
//         */
//        if (userPassword.equals(passwordObject.getOldPassword())){
//            aUser.setUserPassword(passwordObject.getNewPassword());
//            userMapper.updateByPrimaryKeySelective(aUser);
//            LOGGER.info(aUser.toString());
//            flag=true;
//        }
//
//        /**
//         * 以下测试环境时需要取消注释
//         */
////        if (!passwordEncoder.matches(passwordObject.getOldPassword(),userPassword)){
////            throw new BadCredentialsException("原密码不正确，请确认后再确定!");
////        }else {
////            aUser.setUserPassword(passwordEncoder.encode(passwordObject.getNewPassword()));
////            userMapper.updateByPrimaryKeySelective(aUser);
////            LOGGER.info(aUser.toString());
////            flag=true;
////        }
//        if (flag) adminCacheService.delAdmin(authentication.getName());
//
//        return flag;
//    }
//
//
//}
