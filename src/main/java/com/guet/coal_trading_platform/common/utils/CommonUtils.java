package com.guet.coal_trading_platform.common.utils;

import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 集合相关处理工具类
 *
 * @author zisong.wang
 */
public class CommonUtils {

	private CommonUtils() {

	}

	/**
	 * object.toString，如果对象为空则返回空串
	 *
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		return toString(object, "");
	}

	public static String toString(Object object, String defaultString) {
		return object == null ? defaultString : object.toString();
	}

	/**
	 * 从List中获取第一个元素
	 *
	 * @param list
	 *            集合
	 * @param <T>
	 *            元素类型
	 * @return
	 */
	public static <T> T getFirst(List<T> list) {
		if (!isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 把一个字符串转成map，字符串格式：key->v;key->v2;key->3
	 *
	 * @param string
	 * @return
	 */
	public static Map<String, String> string2Map(String string) {
		return string2Map(string, "->", ";");
	}

	/**
	 * 把一个字符串转成map
	 *
	 * @param string
	 *            字符串
	 * @param keySplitStr
	 *            连接k,v的字符串
	 * @param entrySplitStr
	 *            连接每个entry的字符串
	 * @return
	 */
	public static Map<String, String> string2Map(String string, String keySplitStr, String entrySplitStr) {
		Map<String, String> map = new HashMap<String, String>(16);
		if (StringsUtils.isNotBlank(string)) {
			String[] groups = string.split(entrySplitStr);
			for (String group : groups) {
				String[] entry = group.split(keySplitStr);
				if (entry != null && entry.length > 1) {
					map.put(entry[0], entry[1]);
				}
			}
		}
		return map;
	}

	/**
	 * 根据一组可变参数的数组对象生成一个Map，用法如下：
	 * <p>
	 * 
	 * <pre>
	 * 	Maps.map(key,value,key,value....);
	 * </pre>
	 *
	 * @param keyValues
	 *            可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map(Object... keyValues) {
		if (keyValues != null && keyValues.length > 1) {
			Class<?> kClass = keyValues[0].getClass();
			Class<?> vClass = keyValues[1].getClass();
			return (Map<K, V>) map(kClass, vClass, keyValues);
		}
		return null;
	}

	/**
	 * 根据一组可变参数的数组对象生成一个Map，并且有强制类型化，用法如下：
	 * <p>
	 * 
	 * <pre>
	 * Maps.map(key.class,value.class,key,value,key,value,key,value......);
	 * </pre>
	 *
	 * @param kClass
	 *            key类型
	 * @param vClass
	 *            value类型
	 * @param keyValues
	 *            可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
	 * @return
	 */
	public static <K, V> Map<K, V> map(Class<K> kClass, Class<V> vClass, Object... keyValues) {
		Map<K, V> m = new HashMap<K, V>(16);
		int i = 1;
		Object preObj = null;
		for (Object o : keyValues) {
			if (i % 2 == 0) {
				K k = kClass.cast(preObj);
				V v = vClass.cast(o);
				m.put(k, v);
			}
			preObj = o;
			i++;
		}
		return m;
	}

	/**
	 * 根据一组可变参数的数组对象生成一个Map，但不会对K，V使用泛型，用法如下：
	 * <p>
	 * 
	 * <pre>
	 * Maps.map(key,value,key,value,key,value......);
	 * </pre>
	 *
	 * @param keyValues
	 *            可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
	 * @return
	 */
	public static Map<Object, Object> mapByAarray(Object... keyValues) {
		Map<Object, Object> m = new HashMap<Object, Object>(20);
		int i = 1;
		Object key = null;
		for (Object value : keyValues) {
			if (i % 2 == 0) {
				m.put(key, value);
			}
			key = value;
			i++;
		}
		return m;
	}

	/**
	 * 以list方式读取map的值
	 *
	 * @param map
	 *            map
	 * @param <K>
	 *            K类型
	 * @param <V>
	 *            V类型
	 * @return
	 */
	public static <K, V> List<V> valuesOfMap(Map<K, V> map) {
		if (!isEmpty(map)) {
			List<V> list = arrayList();
			for (Entry<K, V> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
			return list;
		}
		return arrayList();
	}

	/**
	 * 判断某个map是否为空
	 *
	 * @param m
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> m) {
		return m == null || m.isEmpty();
	}

	/**
	 * 判断某个集合是否为空
	 *
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		return CollectionUtils.isEmpty(c);
	}

	/**
	 * 判断某个数组是否为空
	 *
	 * @param args
	 * @return
	 */
	public static boolean isEmpty(Object[] args) {
		return args == null || args.length == 0;
	}

	/**
	 * 判断某个数组是否不为空
	 *
	 * @param args
	 * @return
	 */
	public static boolean isNotEmpty(Object[] args) {
		return !isEmpty(args);
	}

	/**
	 * 判断某个集合是否不为空
	 *
	 * @param coll
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param obj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return StringsUtils.isEmpty(obj.toString());
		} else if (obj instanceof Collection) {
			return isEmpty(((Collection) obj));
		} else if (obj instanceof Map) {
			return isEmpty((Map<?, ?>) obj);
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		return !isEmpty(pObj);
	}

	/**
	 * 删除map中的空值
	 *
	 * @param map
	 * @return
	 */
	public static Map<String, Object> removeEmptyFromMap(Map<String, Object> map) {
		Map<String, Object> mapTemp = new HashMap<String, Object>(map.size());
		Iterator<?> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry) iter.next();
			String key = entry.getKey().toString();
			Object val = entry.getValue();
			if (isNotEmpty(val)) {
				mapTemp.put(key, val);
			}
		}
		return mapTemp;
	}

	/**
	 * 创建一个新的arraylist
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> arrayList() {
		return new ArrayList<T>();
	}

	/**
	 * 创建一个新的arraylist
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> arrayList(int len) {
		return new ArrayList<T>(len);
	}

	/**
	 * 创建一个新的链表
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> linkedList() {
		return new LinkedList<T>();
	}

	/**
	 * 返回一个hastSet
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> hastSet() {
		return new HashSet<T>();
	}

	/**
	 * 根据参数创建一个hastSet
	 *
	 * @param objs
	 * @param <T>
	 * @return
	 */
	@SafeVarargs
	public static <T> Set<T> asHastSet(T... objs) {
		Set<T> set = hastSet();
		for (T obj : objs) {
			set.add(obj);
		}
		return set;
	}

	/**
	 * 根据参数创建一个链表
	 *
	 * @param objs
	 * @param <T>
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> asLinkedList(T... objs) {
		List<T> list = linkedList();
		for (T obj : objs) {
			list.add(obj);
		}
		return list;
	}

	/**
	 * 根据参数创建一个arraylist
	 *
	 * @param objs
	 * @param <T>
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> asArrayList(T... objs) {
		List<T> list = arrayList();
		for (T obj : objs) {
			list.add(obj);
		}
		return list;
	}

	/**
	 * 删除数组中为Empty的项
	 *
	 * @param arys
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] removeIfEmpty(T[] arys) {
		if (isEmpty(arys)) {
			return (T[]) new Object[0];
		}
		return toArray(removeIfEmpty(asArrayList(arys)));
	}

	/**
	 * 将某个Collection转成数组
	 *
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> c) {
		if (isEmpty(c)) {
			return (T[]) new Object[0];
		}
		T t = first(c);
		if (isEmpty(t)) {
			return (T[]) new Object[0];
		}
		T[] tArray = (T[]) Array.newInstance(t.getClass(), c.size());
		c.toArray(tArray);
		return tArray;
	}

	/**
	 * 获取Collection中的第一个对象
	 *
	 * @param c
	 *            指定的集合
	 * @return
	 */
	public static <T> T first(Collection<T> c) {
		return isNotEmpty(c) ? IterableUtils.get(c, 0) : null;

	}

	/**
	 * 删除中的空项
	 *
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, C extends Collection<T>> C removeIfEmpty(C c) {
		if (isNotEmpty(c)) {
			List<T> l = arrayList();
			for (T t : c) {
				if (isEmpty(t)) {
					continue;
				}
				l.add(t);
			}
			return (C) l;
		}
		return c;
	}

	/***
	 * 生成订单号方法 时间 + 2位随机数
	 */
	public static String getOrderIdByDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			result += random.nextInt(10);
		}
		return newDate + result;
	}

	/****
	 * 获取随机卡号 时间戳 + 7为随机数
	 * 
	 * @return
	 */
	public static String getRandomIdByDateTime() {
		Long newDate = new Date().getTime();
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			result += random.nextInt(10);
		}
		return newDate + result;
	}

	/****
	 * 获取随机卡密 8位随机数
	 * 
	 * @return
	 */
	public static String getRandomNumber() {
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	public static String getRandomNumber(int size) {
		String result = "";
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(CommonUtils.getRandomNumber(6));
	}

	public static int randomNumberBySize(int size) {
		Random random = new Random();
		return random.nextInt(size);
	}

	public static String getRandomStr() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static <K, V> Map<K, V> parseToMap(String json, Class<K> keyType, Class<V> valueType) {
		return com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<Map<K, V>>(keyType, valueType) {
		});
	}

}
