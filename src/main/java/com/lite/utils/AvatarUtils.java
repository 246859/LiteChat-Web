package com.lite.utils;

/**
 * 随机生成默认头像的类
 */
public class AvatarUtils {
    private static final String AVATAR_DIR = "src/main/resources/static/avator/";

    private static final String BOY_ONE = "boy_avatar_1.svg";

    private static final String BOY_TWO = "boy_avatar_2.svg";

    private static final String BOY_THREE = "boy_avatar_3.svg";

    private static final String GIRL_ONE = "girl_avatar_1.svg";

    private static final String GIRL_TWO = "girl_avatar_2.svg";

    private static final String GIRL_THREE = "girl_avatar_3.svg";

    private static final String[][] AVATAR = new String[][]{
            new String[]{
                    BOY_ONE, BOY_TWO, BOY_THREE
            },
            new String[]{
                    GIRL_ONE, GIRL_TWO, GIRL_THREE
            }
    };

    public static String getDefaultAvatar() {//生成随机的头像

        int gender = 0;
        int index = (int) (Math.random() * 6);

        if (index >= 3) {
            index -= 3;
            gender = 1;
        }

        return AVATAR_DIR + AVATAR[gender][index];
    }

    public static String getDefaultAvatar(Integer gender) {//根据性别生成随机的头像

        int index = (int) (Math.random() * 3);

        if (gender > 1 || gender < 0){
            gender = 1;
        }

        return AVATAR_DIR + AVATAR[gender][index];
    }

}
