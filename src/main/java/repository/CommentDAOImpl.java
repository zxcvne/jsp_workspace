package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Comment;
import orm.DatabasesBuilder;


public class CommentDAOImpl implements CommentDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabasesBuilder();
		sql = DatabasesBuilder.getFactory().openSession();
	}

	@Override
	public int insert(Comment c) {
		// insert, update, delete => commit
		int isOk = sql.insert("commentMapper.add", c);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public List<Comment> getList(int bno) {
		
		return sql.selectList("commentMapper.list",bno);
	}

	@Override
	public int delete(int cno) {
		int isOk = sql.delete("commentMapper.del", cno);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public int update(Comment c) {
		int isOk = sql.update("commentMapper.mod", c);
		if(isOk > 0) sql.commit();
		return isOk;
	}
	
}
