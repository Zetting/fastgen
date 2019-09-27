package com.fastgen.core.util;

/**
 * @author: zet
 * @date: 2019/9/12 0:05
 */
public class PageUtil {
    public static int[] transToStartEnd(int pageNo, int pageSize) {
        int start = getStart(pageNo, pageSize);
        if (pageSize < 1) {
            pageSize = 0;
        }

        int end = start + pageSize;
        return new int[]{start, end};
    }
    public static int getStart(int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }

        return (pageNo - 1) * pageSize;
    }
}
