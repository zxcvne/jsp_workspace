package service;

import java.util.List;

import domain.Comment;

public interface CommentService {

	int insert(Comment c);

	List<Comment> getList(int bno);

}
