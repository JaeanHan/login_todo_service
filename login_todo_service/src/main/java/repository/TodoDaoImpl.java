package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBConnectionMgr;
import entity.Todo;

public class TodoDaoImpl implements TodoDao {
	DBConnectionMgr pool = DBConnectionMgr.getInstance();

	@Override
	public int addOrUpdateTodo(Todo todo, int num) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0;
		
		try {
			con=pool.getConnection();
			sb.append("insert into todos values(?, ?, ?, ?, ?, now(), now())");
			
			//num is 0 if selected new
			if(num != 0) {
				sb.append("on duplicate key update toDo=?, state=?, importance=?, update_date=now()");				
			}
			
			pstmt=con.prepareStatement(sb.toString());
			
			pstmt.setInt(1, num==0 ? 0 : todo.getTodocode());
			pstmt.setInt(2, todo.getUsercode());
			pstmt.setString(3, todo.getTodo());
			pstmt.setString(4, todo.getState());
			pstmt.setInt(5, todo.getImportance());
			
			if(num != 0) {
				pstmt.setString(6, todo.getTodo());
				pstmt.setString(7, todo.getState());
				pstmt.setInt(8, todo.getImportance());	
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

	@Override
	public Todo getTodo(Todo todo) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Todo todoFound = null;
		
		try {
			con = pool.getConnection();
			sql = "select * from todos where todocode=? and usercode=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, todo.getTodocode());
			pstmt.setInt(2, todo.getUsercode());
			rs=pstmt.executeQuery();
			rs.next();
			
			todoFound = Todo.builder()
					.todocode(rs.getInt(1))
					.usercode(rs.getInt(2))
					.todo(rs.getString(3))
					.state(rs.getString(4))
					.importance(rs.getInt(5))
					.create_date(rs.getTimestamp(6).toLocalDateTime())
					.update_date(rs.getTimestamp(7).toLocalDateTime())
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return todoFound;
	}
	
	private int getTodoAmount(String username) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con=pool.getConnection();
			sql = "SELECT\n"
					+ "	COUNT(td.todocode)\n"
					+ "FROM\n"
					+ "	todos td LEFT OUTER JOIN user u ON td.usercode=u.usercode\n"
					+ "WHERE\n"
					+ "	u.username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

	@Override
	public ArrayList<Todo> getTodosByUsername(String username) {
		ArrayList<Todo> todoList = new ArrayList<Todo>();
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int amount = 0;
		
		try {
			con = pool.getConnection();
			sql = "SELECT\n"
					+ "	td.*\n"
					+ "FROM\n"
					+ "	todos td LEFT OUTER JOIN user u ON td.usercode = u.usercode\n"
					+ "WHERE\n"
					+ "	username=\"jaean\"\n"
					+ "ORDER BY\n"
					+ "	td.todocode";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			rs.next();
			
			amount = getTodoAmount(username);
			
			//while(rs.next())?
			for(int i = 0; i < amount; i++) {
				Todo temp = Todo.builder()
						.todocode(rs.getInt(1 + (7*i)))
						.usercode(rs.getInt(2 + (7*i)))
						.todo(rs.getString(3 + (7*i)))
						.state(rs.getString(4 + (7*i)))
						.importance(rs.getInt(5 + (7*i)))
						.create_date(rs.getTimestamp(6 + (7*i)).toLocalDateTime())
						.update_date(rs.getTimestamp(7 + (7*i)).toLocalDateTime())
						.build();
				
				todoList.add(temp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return todoList;
	}

	@Override
	public int deleteTodo(Todo todo) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "delete from todos where todocode=? and usercode=?";
			pstmt = con.prepareStatement(sql);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

}
