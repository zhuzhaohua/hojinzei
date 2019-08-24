package com.kobuta.rakuchin.hojinzei.utils;

import java.util.List;

public class ArrayUtil {

    public static boolean isNotEmpty(List obj) {
        if (obj == null) {
            return false;
        }

        return obj.size() > 0;


    }

    public static boolean isEmpty(List obj) {
        if (null == obj || obj.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] obj) {

        if (obj == null) {
            return false;
        }

        return obj.length > 0;

    }


//    public static boolean isNotEmpty(Object obj) {
//
//        boolean b1 = obj == null;
//        if (b1) {
//            return false;
//        }
//
//
//        boolean b2 =true;
//        if (obj instanceof List) {
//            b2 =  ((List)obj).size() > 0;
//        }
//        else if (obj.getClass().isArray()) {
//            b2 =  ((Object[])obj).length > 0;
//        }
//        else {
//
//        }
//
//        return b2;
//
//    }
}
