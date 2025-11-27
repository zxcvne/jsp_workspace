package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import orm.DatabasesBuilder;

public class BoardDAOImpl implements BoardDAO {

	// 로그 객체
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	private SqlSession sql;
	
	public BoardDAOImpl() {		
		new DatabasesBuilder();
		sql = DatabasesBuilder.getFactory().openSession();
	}

	@Override
	public int insert(Board b) {
		// sql.메서드(namespace.id, object);
		// 메서드 => select, insert, update, delete
		log.info("BoardDAOImpl Test...");
		int isOk = sql.insert("boardMapper.add", b);
		
		// insert, update, delete DB 자체의 값이 변경되는 구문
		// 반드시 commit을 해주어야 반영이 됨. => transactionManager 자체 운영
		if(isOk > 0) sql.commit();
		
		return isOk;
	}

	@Override
	public List<Board> getList() {
		// select는 조회만 하기 때문에 commit 불필요
		List<Board> list = sql.selectList("boardMapper.list");
		return list;
	}

	@Override
	public Board getDetail(int bno) {
		
		return sql.selectOne("boardMapper.detail", bno);
	}

	@Override
	public int update(Board b) {
		int isOk = sql.update("boardMapper.update", b);
		if(isOk > 0) sql.commit();
		return isOk;
	}


	@Override
	public int delete(int bno) {
		int isOk = sql.update("boardMapper.del", bno);
		if(isOk > 0) sql.commit();
		return isOk;
	}
	
}
