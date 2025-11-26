package orm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DatabasesBuilder {

//	String resource = "org/mybatis/example/mybatis-config.xml";
//	InputStream inputStream = Resources.getResourceAsStream(resource);
//	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	
	private static SqlSessionFactory factory;
	private static final String CONFIG = "orm/mybatisConfig.xml";
	
	static {
		// 초기화
		try {
			factory = new SqlSessionFactoryBuilder().build(
					Resources.getResourceAsReader(CONFIG)
					);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getFactory() {
		return factory;
	}
	
	
}
