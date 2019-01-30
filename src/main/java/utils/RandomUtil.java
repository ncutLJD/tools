package utils;

import java.util.Random;

/**
 * 随机数生成
 */
public class RandomUtil {

    //生成随机数字和字母
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                //int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                int temp = 65;
                val.append ((char)(random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    //int randNumber =rand.nextInt(MAX - MIN + 1) + MIN;  randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数
    public static Integer getRandNumber(int Min,int Max){
        //范围
        int bound = Max - Min + 1;
        Random rand = new Random();
        int randNumber = rand.nextInt(bound) + Min;
        return randNumber;
    }
}
