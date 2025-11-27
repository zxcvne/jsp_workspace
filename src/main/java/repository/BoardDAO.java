package repository;

import java.util.List;

import domain.Board;

public interface BoardDAO {

	int insert(Board b);

	List<Board> getList();

	Board getDetail(int bno);

	int update(Board b);

	int delete(int bno);

}
