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
	
}
