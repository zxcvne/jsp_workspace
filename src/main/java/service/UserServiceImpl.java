package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.User;
import repository.UserDAO;
import repository.UserDAOImpl;

public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO udao;
	
	public UserServiceImpl() {
		udao = new UserDAOImpl();
	}

	@Override
	public int insert(User user) {
		return udao.insert(user);
	}
}
