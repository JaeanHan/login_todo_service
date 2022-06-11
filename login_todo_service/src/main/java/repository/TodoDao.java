package repository;

import java.util.ArrayList;

import entity.Todo;

public interface TodoDao {
	public int addOrUpdateTodo(Todo todo, int num);
	public ArrayList<Todo> getTodosByUsercode(int usercode);
	public int deleteTodo(int usercode, int todocode);
	public int getTodoCode(int usercode, String todo);
//	public int updateTodobyBtn();
}
