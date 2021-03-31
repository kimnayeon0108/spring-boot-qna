package com.codessquad.qna;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PageManager {

    private static final int COUNT_OF_QUESTION_ON_PAGE = 5;

    private static List<Pageable> pages = new ArrayList<>();

    private PageManager() {
    }

    public static int getPageCount(int questionCount) {
        int pageCount = questionCount / COUNT_OF_QUESTION_ON_PAGE;
        if (questionCount % COUNT_OF_QUESTION_ON_PAGE == 0) {
            return pageCount;
        }
        return pageCount + 1;
    }

    public static void add(Pageable pageable) {
        pages.add(pageable);
    }

    public static List<Pageable> getPages() {
        return pages;
    }
}
