package repository;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.User;
import orm.DatabasesBuilder;

public class UserDAOImpl implements UserDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	private SqlSession sql;
	
	public UserDAOImpl() {
		new DatabasesBuilder();
		sql = DatabasesBuilder.getFactory().openSession();
	}

	@Override
	public int insert(User user) {
		int isOk = sql.insert("userMapper.add", user);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public User getUser(User user) {
		return sql.selectOne("userMapper.getUser", user);
	}

	@Override
	public int lastLoginUpdate(String id) {
		int isOk = sql.update("userMapper.lastLoginUpdate", id);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public int update(User user) {
		int isOk = sql.update("userMapper.update", user);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public int delete(String id) {
		int isOk = sql.delete("userMapper.del", id);
		if(isOk > 0) sql.commit();
		return isOk;
	}
	
}
