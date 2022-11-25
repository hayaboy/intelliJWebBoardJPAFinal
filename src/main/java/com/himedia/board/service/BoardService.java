package com.himedia.board.service;

import com.himedia.board.domain.Board;
import com.himedia.board.domain.Search;
import org.springframework.data.domain.Page;

public interface BoardService {

    void insertBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Board board);

    Board getBoard(Board board);

    public Page<Board> getBoardList(Search search);
}
