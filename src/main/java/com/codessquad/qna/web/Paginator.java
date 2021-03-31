package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    public static final int QUESTION_BY_PAGE = 5;
    private static final int BLOCK_SIZE = 5;

    private List<Question> questions;
    private List<Integer> pageIndexes;
    private int totalPages;
    private int currentBlock;
    private int currentPage;

    public Paginator(Page<Question> page, int currentPage) {
        this.questions = page.getContent();
        this.pageIndexes = new ArrayList<>();
        this.totalPages = page.getTotalPages();
        this.currentBlock = currentPage / BLOCK_SIZE;
        this.currentPage = currentPage;
    }

    public Model paginate(Model model) {
        model.addAttribute(getPageIndexes(model));
        model.addAttribute(getPrevBlock(model));
        model.addAttribute(getNextBlock(model));
        return model;
    }

    private Model getPageIndexes(Model model) {
        if (currentPage % BLOCK_SIZE == 0) {
            currentBlock--;
        }
        for (int i = currentBlock * BLOCK_SIZE + 1; i <= currentBlock * BLOCK_SIZE + BLOCK_SIZE; i++) {
            if (i > totalPages) break;
            pageIndexes.add(i);
        }
        model.addAttribute("pageIndexes", pageIndexes);
        model.addAttribute("questions", questions);
        return model;
    }

    private Model getPrevBlock(Model model) {
        int prevBlock = currentBlock - 1;
        if (prevBlock < 0) {
            prevBlock = 0;
        }
        if (currentBlock > 0) {
            int prevPage = prevBlock * BLOCK_SIZE + 1;
            model.addAttribute("prevPage", prevPage);
        }
        return model;
    }

    private Model getNextBlock(Model model) {
        int nextBlock = currentBlock + 1;
        int totalBlocks = totalPages / BLOCK_SIZE;

        if (nextBlock > totalBlocks) {
            nextBlock = totalBlocks;
        }
        if (currentBlock < totalBlocks) {
            int nextPage = nextBlock * BLOCK_SIZE + 1;
            model.addAttribute("nextPage", nextPage);
        }
        return model;
    }
}
