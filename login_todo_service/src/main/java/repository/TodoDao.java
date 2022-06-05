package repository;

import java.util.ArrayList;

import entity.Todo;

public interface TodoDao {
	public int addOrUpdateTodo(Todo todo, int num);
	public Todo getTodo(Todo todo);
	public ArrayList<Todo> getTodosByUsername(String username);
	public int deleteTodo(int usercode, String todo);
	public int getTodoCode(int usercode, String todo);
	
}
