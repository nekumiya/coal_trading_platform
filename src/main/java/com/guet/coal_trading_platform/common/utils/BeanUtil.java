package com.guet.coal_trading_platform.common.utils;

import com.guet.coal_trading_platform.exceptions.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by 欲隐君。 on 2021/3/27
 */
public class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public BeanUtil() {
    }

    public static <T, F> List<T> convert(List<F> list, Class<T> targetClass) {
        if(targetClass == null){
            throw new MyException("targetClass is null  请设置目标类型");
        }
        List<T> rs = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Object source = var3.next();

                try {
                    T target = targetClass.newInstance();
                    BeanUtils.copyProperties(source, target);
                    rs.add(target);
                } catch (Exception var6) {
                    throw new MyException("bean convert exception");
                }
            }
        }

        return rs;
    }

    public static <S, R> R convert(S source, Class<R> targetClass) {
        try {
            if (source == null) {
                logger.error("bean copy fail. case source is null");
                return null;
            } else {
                R target = targetClass.newInstance();
                BeanUtils.copyProperties(source, target);
                return target;
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
