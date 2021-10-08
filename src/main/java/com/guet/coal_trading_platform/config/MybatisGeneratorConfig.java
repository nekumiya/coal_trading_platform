package com.guet.coal_trading_platform.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 欲隐君。 on 2021/3/13
 */

@Configuration
@MapperScan({"com.guet.coal_trading_platform.mbg.mapper","com.guet.coal_trading_platform.dao"})
public class MybatisGeneratorConfig {
}
