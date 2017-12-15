package com.hesen.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

//import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Created by jc on 2017/8/2.
 */

public class SysUtil {

    public static final String EMPTY_STRING = "";

    public static void initBean(Object beanInstance) throws Exception {
        Class<?> cla = beanInstance.getClass();
        Method[] methods = cla.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.length()>3) {
                Field field = null;
                char fieldFirstChar = methodName.charAt(3);
                if (fieldFirstChar >= 65 && fieldFirstChar <=90) {
                    fieldFirstChar +=32;
                }
                field = cla.getDeclaredField(fieldFirstChar + methodName.substring(4));
                if (field != null) {
                    Class<?>[] methodParamClass = method.getParameterTypes();
                    for (Class<?> mpc : methodParamClass) {
                        if (mpc.isPrimitive()) {
                            //Object initVal = PRIMITIVE_VALUE.get(mpc.getName());
                            //method.invoke(beanInstance, new Object[]{initVal});
                        } else {
                            method.invoke(beanInstance, new Object[1]);
                        }
                    }
                }
            }
        }

    }

    public static void mergeBean(Object destBean, Object origBean) throws Exception{
        Objects.requireNonNull(destBean,"");
        Objects.requireNonNull(origBean,"");
        Class<?> destClass = destBean.getClass();
        Class<?> origClass = origBean.getClass();
        if (destBean != origBean) {
            return;
        }

        Field[] fields = destClass.getDeclaredFields();
        for (Field destField : fields) {
            destField.setAccessible(true);
            if ("serialVersionUID".equals(destField.getName())) {
                continue;
            }
            Class<?> destFieldTypeClass = destField.getType();
            String filedClassName = destFieldTypeClass.getSimpleName();

            Object destFieldVal = destField.get(destBean);
            Object origFieldVal = null;

            if(destFieldTypeClass.isPrimitive()) {
                Object primitiveInitVal = Class.forName(filedClassName);
                if (primitiveInitVal.equals(destFieldVal)) {
                    origFieldVal = destField.get(origBean);
                    if (!primitiveInitVal.equals(origFieldVal)) {
                        destField.set(destBean, origFieldVal);
                    }
                }
            } else {

            }

        }


    }

    //0相等，1大于，-1小于，-2异常
    public static int compareBersion(String dbVersion, String currentVersion) {
        int ret = -2;
        if (SysUtil.isNotEmpty(dbVersion) && SysUtil.isNotEmpty(currentVersion)) {
            dbVersion = dbVersion.trim();
            currentVersion = currentVersion.trim();
            if (dbVersion.equals(currentVersion))
                return 0;

            String[] dbVersionArray = dbVersion.split("\\.");
            String[] currentVersionArray = currentVersion.split("\\.");

            if (dbVersionArray.length != currentVersionArray.length)
                return -2;

            for (int index = 0; index < dbVersionArray.length; index++) {
                String dbV = dbVersionArray[index];
                String cv = currentVersionArray[index];
                if (StringUtils.isNumeric(dbV) && StringUtils.isNumeric(cv)) {
                    try {
                        Integer db = Integer.parseInt(dbV);
                        Integer c = Integer.parseInt(cv);
                        if (db > c)
                            return 1;
                        if (db < c)
                            return -1;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return -2;
                    }
                } else {
                    return -2;
                }
            }
        }
        return ret;
    }


    public static String formatValue(int i) {
        return String.format("%1$,02d", i);
    }

    public static String getDecimalFormatValue(double p) {
        DecimalFormat nf = (DecimalFormat) NumberFormat.getPercentInstance();
        nf.applyPattern("00.00%"); //00表示小数点2位
        nf.setMaximumFractionDigits(2); //2表示精确到小数点后2位
        return nf.format(p);
    }

    public static String defaultString(String str) {
        return isEmpty(str) ? "default" : str;
    }

    public static boolean isEmpty(String[] array) {
        for (String str : array) {
            if (isEmpty(str))
                return true;
        }
        return false;
    }

    public static boolean isNumeric(String str){
        return org.apache.commons.lang3.StringUtils.isNumeric(str);
    }

    /**
     * 是否为空
     *
     * @param str
     * @return
     * @author 魏柱
     * @date 2013-3-28 下午4:30:02
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 截取字符串
     *
     * @return
     * @author stone
     * @time 2012-11-13下午10:23:46
     */
    public static String substring(String str, int length, String s) {
        if (str == null || str.isEmpty() || str.length() < length) {
            return str;
        }
        str = str.substring(0, length - 1);
        return str + s;
    }

    /**
     * 生成随机字符串
     *
     * @return String
     */
    public static String UUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("-", "");
        return str;
    }

    /**
     * 生成固定长度的随机字符串
     * @param len 需要生成的长度
     * @return
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "0123456789abcdefghigklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            stringBuilder.append(nowStr);
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return stringBuilder.toString();
    }

    /**
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串
     *
     * @param s            原始字符串
     * @param srcEncoding  源编码方式
     * @param destEncoding 目标编码方式
     * @return 转换编码后的字符串，失败返回原始字符串
     */
    public static String getString(String s, String srcEncoding,
                                   String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     *
     * @param b        字节数组
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, String encoding) {
        try {
            return new String(b, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b);
        }
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     *
     * @param b        字节数组
     * @param offset   要转换的起始位置
     * @param len      要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len,
                                   String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }

    /**
     * 截取字符串，包含中文
     *
     * @return
     */
    public final static String cutTrueLength(String strContent,
                                             int maxTrueLength) {
        if (strContent == null || maxTrueLength <= 0) {
            return "";
        }

        int trueLen = trueLength(strContent);
        String strNew = strContent;
        if (trueLen > maxTrueLength) {
            for (int i = strContent.length() - 1; i > 0; i--) {
                strNew = strContent.substring(0, i);

                if (trueLength(strNew) == maxTrueLength) {
                    break;
                } else if (trueLength(strNew) < maxTrueLength) {
                    strNew += "";
                    break;
                }
            }
        }

        return strNew + "...";
    }

    /**
     * 字符串长度，包含中文
     *
     * @param strContent
     * @return
     */
    public final static int trueLength(String strContent) {
        int lengthTotal = 0;// 长度总计

        int n = strContent.length();

        String strWord = "";

        int asc;

        for (int i = 0; i < n; i++) {
            strWord = strContent.substring(i, i + 1);
            asc = strWord.charAt(0);
            if (asc < 0 || asc > 127)
                lengthTotal = lengthTotal + 2;
            else
                lengthTotal = lengthTotal + 1;
        }

        return lengthTotal;
    }

    public static String abbreviate(String str, int width, String ellipsis) {
        if (str == null || "".equals(str)) {
            return "";
        }

        int d = 0; // byte length
        int n = 0; // char length
        for (; n < str.length(); n++) {
            d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
            if (d > width) {
                break;
            }
        }

        if (d > width) {
            n = n - ellipsis.length() / 2;
            return str.substring(0, n > 0 ? n : 0) + ellipsis;
        }

        return str = str.substring(0, n);
    }

    /**
     * @return String Object
     */
    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }


    @SuppressWarnings("rawtypes")
    public static boolean isSetEqual(Set set1, Set set2) {

        if (set1.size() != set2.size()) {
            return false;
        }

        Iterator ite1 = set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;
        while (ite1.hasNext()) {
            if (!set2.contains(ite2.next())) {
                isFullEqual = false;
            }
        }

        return isFullEqual;
    }

    public static boolean isAllNotEmpty(String... strs) {
        if (ArrayUtils.isEmpty(strs)) return false;
        for (String str : strs) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 深度克隆对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T t) throws Exception {

        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(t);

        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (T) (oi.readObject());
    }

    /**
     * 除去Map中的空值和指定参数Key
     *
     * @param params
     * @return 去掉空值与指定参数Key后的新Map
     */
    public static Map<String, String> paraFilter(Map<String, String> params, String... filterKeys) {
        Map<String, String> result = new HashMap<String, String>();

        if (params == null || params.size() <= 0) {
            return result;
        }
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null || value.equals("") || ArrayUtils.contains(filterKeys, key)) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }


    /**
     * 把Map所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String preStr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                preStr = preStr + key + "=" + value;
            } else {
                preStr = preStr + key + "=" + value + "&";
            }
        }

        return preStr;
    }

    /**
     * 获取一个四位随机数，并且四位数不重复
     *
     * @return String
     */
    public static String getRandomNumber(int size) {
        // 使用SET以此保证写入的数据不重复
        Set<Integer> set = new HashSet<Integer>();
        // 随机数
        Random random = new Random();

        while (set.size() < size) {
            // nextInt返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）
            // 和指定值（不包括）之间均匀分布的 int 值。
            set.add(random.nextInt(10));
        }
        return StringUtils.join(set, "");
    }

    public static String getRandomNumber() {
        return getRandomNumber(4);
    }

    public static String toUtf8String(String str) {
        try {
            if (str != null)
                return new String(str.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
/*    public static String encodeFilename(String filename, HttpServletRequest request) {
        *//**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         *//*
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                //return MimeUtility.encodeText(filename, "UTF-8", "B");
                return new String(filename.getBytes("utf-8"), "iso-8859-1");
            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }*/

    public static String objectToString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return truncValue(obj.toString());
    }

    public static String truncValue(String str) {
        if (isEmpty(str)) {
            return EMPTY_STRING;
        }
        return str;
    }

    public static String valueOf(Object obj){
        if(obj == null){
            return "";
        }
        return obj.toString();
    }

    public static String retainsArray(String a, String b) {
        StringBuffer al = new StringBuffer();
        for (String at : a.split(",")) {
            for (String bt : b.split(",")) {
                if (at.equals(bt)) {
                    al.append(at + ",");
                    break;
                }
            }
        }
        if (al.toString().length() > 0)
            al.setLength(al.length() - 1);
        return al.toString();
    }

    public static String convertString(String src, String APISrcCharSet, String APIDestCharSet) {
        String ret = src;
        if (src != null) {
            try {
                ret = new String(src.getBytes(APISrcCharSet), APIDestCharSet);
            } catch (UnsupportedEncodingException e) {
                ret = src;
            }
        }
        return ret;
    }

//    /**
//     * @param localVersion  String
//     * @param serverVersion String
//     * @return 0 = same, -1 = local version newer than server, 1 = local version
//     *         old than server, -2 unknown
//     */
//    public static Integer checkVersion(String localVersion, String serverVersion) {
//        try {
//            if (isEmpty(localVersion) || isEmpty(serverVersion)) {
//                return -2;
//            }
//            String[] localVs = localVersion.split("\\.");
//            String[] serverVs = serverVersion.split("\\.");
//
//            int index = Math.min(localVs.length, serverVs.length);
//            for (int i = 0; i < index; i++) {
//                if (NumberUtil.intValue(localVs[i]).intValue() == NumberUtil.intValue(serverVs[i]).intValue()) {
//                    continue;
//                } else if (NumberUtil.intValue(localVs[i]).intValue() < NumberUtil.intValue(serverVs[i]).intValue()) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            }
//        } catch (Exception e) {
//            return 1;
//        }
//        return 0;
//    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    @SuppressWarnings("unused")
    public static List<String> readFileByLines(String fileName) {
        List<String> lineList = new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                lineList.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return lineList;
    }


    /**
     * 将InputStream转换成String
     *
     * @param in InputStream
     * @return String
     * @throws Exception
     */
    public static String inputStreamTOString(InputStream in) throws Exception {
        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }

    public static String exceptionToString(Exception e){
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer,true));
        return writer.toString();
    }
    public static String throwableToString(Throwable t){
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer,true));
        return writer.toString();
    }


    /**
     * 默认false  关闭
     * @return
     */
    public static boolean isScheduler() {
        return Boolean.parseBoolean(System.getProperty("scheduler.on","false"));
    }

    /**
     * 知识的补充
     *
     * InetAddress 继承自 java.lang.Object类
     * 它有两个子类：Inet4Address 和 Inet6Address
     * 此类表示互联网协议 (IP) 地址。
     *
     * IP 地址是 IP 使用的 32 位或 128 位无符号数字，
     * 它是一种低级协议，UDP 和 TCP 协议都是在它的基础上构建的。
     *
     * ************************************************
     * 主机名就是计算机的名字（计算机名），网上邻居就是根据主机名来识别的。
     * 这个名字可以随时更改，从我的电脑属性的计算机名就可更改。
     *  用户登陆时候用的是操作系统的个人用户帐号，这个也可以更改，
     *  从控制面板的用户界面里改就可以了。这个用户名和计算机名无关。
     */

    /**
     * 获取本机的IP
     * @return Ip地址
     */
    public static String getLocalHostIP() {
        String ip;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**返回 IP 地址字符串（以文本表现形式）*/
            ip = addr.getHostAddress();
        } catch(Exception ex) {
            ip = "";
        }

        return ip;
    }

    /**
     * 或者主机名：
     * @return
     */
    public static String getLocalHostName() {
        String hostName;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**获取此 IP 地址的主机名。*/
            hostName = addr.getHostName();
        }catch(Exception ex){
            hostName = "";
        }

        return hostName;
    }

    /**
     * 获得本地所有的IP地址
     * @return
     */
    public static String[] getAllLocalHostIP() {

        String[] ret = null;
        try {
            /**获得主机名*/
            String hostName = getLocalHostName();
            if(hostName.length()>0) {
                /**在给定主机名的情况下，根据系统上配置的名称服务返回其 IP 地址所组成的数组。*/
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if(addrs.length>0) {
                    ret = new String[addrs.length];
                    for(int i=0 ; i< addrs.length ; i++) {
                        /**.getHostAddress()   返回 IP 地址字符串（以文本表现形式）。*/
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        }catch(Exception ex) {
            ret = null;
        }

        return ret;
    }

    public static String generateNo(String prefix) {
        return SysUtil.truncValue(prefix) + DateUtil.dateToWantStr(new Date(), "yyMMddHHmmssSSS") + String.format("%03d", (int) (ThreadLocalRandom.current().nextDouble() * 1000));
    }



/*    public static void main(String[] args) throws Exception {
        System.out.println(SysUtil.formatValue(1231));
//        System.out.println(DateUtil.convertDateDateShortToDateLong(DateUtil.DATE_STRING_FORMAT_ALL, new Date()));
//        System.out.println(DateUtil.getTheDayTime2());
//        System.out.println(System.currentTimeMillis());
//        System.out.println(DateUtil.format("yyMMddHHmmssSSS", new Date()));
//        System.out.println(DateUtil.format("yyMMddHHmm", new Date()));
//        System.out.println(System.currentTimeMillis() - DateUtil.convertDateStringToDateLong("yyMMddHHmm", DateUtil.format("yyMMddHHmm", new Date())));
        System.out.println(DateUtil.convertDateLongToDateString(DateUtil.DATE_STRING_FORMAT_ALL, 1438946313219l));
//        System.out.println(DateUtil.convertDateLongToDateString(DateUtil.DATE_STRING_FORMAT_ALL, 1438659250151l));
//        System.out.println(DateUtil.convertDateStringToDateLong(DateUtil.DATE_STRING_FORMAT_ALL, "2015-08-03 16:00:00"));

    }*/

    public static String getFirstNotEmptyStr(String ... arrStr) {
        if(ArrayUtils.isNotEmpty(arrStr)){
            for(String str : arrStr){
                if(StringUtils.isNotEmpty(str)){
                    return str;
                }
            }
        }
        return "";
    }

    /**
     * 将一组String ...arrStr 组装起来
     * @param splitRegex
     * @param arrStr
     * @return
     */
    public static String joinStringArr(String splitRegex,String ... arrStr){
        if(ArrayUtils.isEmpty(arrStr)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(String str : arrStr){
            if(StringUtils.isNotEmpty(str)){
                if(sb.length()>0 && splitRegex != null){
                    sb.append(splitRegex);
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 去掉内容中的a标签
     */
    public static String removeATag(String content){
        if (SysUtil.isEmpty(content)){
            return "";
        }
        String result = content;
        StringBuilder stringBuilder = null;
        Pattern p = Pattern.compile("([\\s\\S]*)<a[^>]*?>([\\s\\S]*)</a>([\\s\\S]*)");//匹配<title>开头，</title>结尾的文档
        while (result.contains("</a>")){
            stringBuilder = new StringBuilder();
            Matcher m = p.matcher(result);//开始编译
            while (m.find()) {
                stringBuilder.append(m.group(1));
                stringBuilder.append(m.group(2));
                stringBuilder.append(m.group(3));
            }
            result = stringBuilder.toString();
        }

        return result;
    }

    /**
     * 删除文件
     */
    public static void deleteFile(File file){
        if (file != null && file.exists()){
            file.delete();
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String fileName){
        File file = new File(fileName);
        if (file != null && file.exists()){
            file.delete();
        }
    }
}
