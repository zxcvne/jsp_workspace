package service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	
	// 로그객체 
	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	// BoardDAO 인터페이스 생성
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int insert(Board b) {
		log.info("BoardServiceImpl Test...");
		return bdao.insert(b);
	}
}
