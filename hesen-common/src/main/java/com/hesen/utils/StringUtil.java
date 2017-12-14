package com.hesen.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {
    private final static Log LOGGER = LogFactory.getLog(StringUtil.class);

    /**
     * MD5 encryption
     *
     * @param str
     * @return
     */
    public static String makeMD5(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String pwd = new BigInteger(1, md.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return str;
    }

    /**
     * Get hash value
     *
     * @param map
     * @return
     */
    public final static String mapToString(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        return map.toString().replaceAll("=", ":").replaceAll(",", ";")
                .replaceAll("\\{", "").replaceAll("\\}", "");
    }

    /**
     * Generate UUID primary key
     *
     * @return
     */
    public static String genUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Cut out of administrative codes
     *
     * @param areaCode
     * @param level
     * @return
     */
    public static String trimAreaCode(String areaCode, Integer level) {

        String startAreaCode = "";
        if (areaCode != null) {
            if (level == 1) {
                startAreaCode = areaCode.substring(0, 2);
            } else if (level == 2) {
                startAreaCode = areaCode.substring(0, 4);
            }
        }
        return startAreaCode;
    }

    /**
     * Determine whether a character is empty or null
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }

        return true;
    }

    public static String setNoNull(Object o) {
        if (o == null)
            return "";
        return o.toString();
    }

    /**
     * Generating random numbers of arbitrary length, that is letter mixed number
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if( "char".equalsIgnoreCase(charOrNum) ) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
     * Generating random numbers of arbitrary length
     * 
     * @param length
     * @return
     */
    public static String getAnyLengthRandom(int length){
    	
    	String val = "";
    	
    	for (int i=0; i<length; i++) {
			int radomInt = new Random().nextInt(10);
			val += radomInt;
		}
    	
    	return val;
    }
    
    /**
     * Replacement action
     *
     * @param str		replaced string
     * @param regular	regular expression
     * @return
     */
    private static String replaceAction(String str, String regular) {
        return str.replaceAll(regular, "*");
    }

    /**
     * The ID number is replaced with "*", and retained first four, 
     * else retained last four
     *
     * If ID number is empty or null, return null; otherwise, 
     * return the replaced string;
     *
     * @param idCard	ID Number
     * @return
     */
    public static String idCardReplaceWithStar(String idCard) {

        if (idCard.isEmpty() || idCard == null) {
            return null;
        } else {
            return replaceAction(idCard, "(?<=\\d{4})\\d(?=\\d{4})");
        }
    }

    /**
     * Bank card replacement, retained last four
     *
     * If the card number is empty or null, return null; otherwise, 
     * return the replaced string;
     *
     * @param bankCard
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {

        if (bankCard.isEmpty() || bankCard == null) {
            return null;
        } else {
            return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
        }
    }
    
    /**
     * username replacement
     * 
     * If the username is empty or null, return to null, otherwise continue;
     * If the user name length is less than 2, return to null, otherwise return 
     * the replaced string;
     * 
     * @param username
     * @return
     */
    public static String nameReplaceWithStar(String username){
    	
    	if (username.isEmpty() || username == null) {
            return null;
        } else{
        	if (username.length() == 2) {
				return replaceAction(username, "(?<=.{1}).");
			}else if (username.length() > 2) {
				
				/*the first method*/
	        	String firstWord = String.valueOf(username.charAt(0));
	        	String lastWord = String.valueOf(username.charAt(username.length()-1));
	            return firstWord+"*"+lastWord;
				
				/*the second method*/
//				String first = username.substring(0,1);
//				String last = username.substring(username.length()-1);
//				return first+"*"+last;
				
				/*the third method*/
//	        	StringBuffer sb = new StringBuffer();
//	        	char first = username.charAt(0);
//	        	char last = username.charAt(username.length()-1);
//	        	sb.append(first).append("*").append(last);
//	        	return sb.toString();
			}else {
				return null;
			}
        }
    }
    
    /**
     * Determine if the string exists
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Get a random number, and there are no duplicates
     *
     * @return String
     */
    public static String getRandomNumber(int size) {
    	
        // Use Set<T> to ensure that there are no duplicates
        Set<Integer> set = new HashSet<Integer>();
        Random random = new Random();

        while (set.size() < size) {
            set.add(random.nextInt(10));
        }
        return StringUtils.join(set, "");
    }
}
