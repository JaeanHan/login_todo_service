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
			
			//num is 1 if new
			// -1 if existing
			if(num == -1) {
				sb.append("on duplicate key update toDo=?, state=?, importance=?, update_date=now()");				
			}
			
			pstmt=con.prepareStatement(sb.toString());
			
			pstmt.setInt(1, todo.getTodocode());
			pstmt.setInt(2, todo.getUsercode());
			pstmt.setString(3, todo.getTodo());
			pstmt.setString(4, todo.getState());
			pstmt.setInt(5, todo.getImportance());
			
			if(num == -1) {
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
	public ArrayList<Todo> getTodosByUsercode(int usercode) {
		ArrayList<Todo> todoList = new ArrayList<Todo>();
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			sql = "SELECT\n"
					+ "	td.*\n"
					+ "FROM todos td LEFT OUTER JOIN user u ON td.todocode = u.usercode\n"
					+ "WHERE\n"
					+ "	td.usercode = ?\n"
					+ "ORDER BY td.importance DESC, create_date;";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, usercode);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Todo temp = Todo.builder()
						.todocode(rs.getInt(1))
						.usercode(rs.getInt(2))
						.todo(rs.getString(3))
						.state(rs.getString(4))
						.importance(rs.getInt(5))
						.create_date(rs.getTimestamp(6).toLocalDateTime())
						.update_date(rs.getTimestamp(7).toLocalDateTime())
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
	public int deleteTodo(int usercode, int todocode) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "delete from todos where todocode=? and usercode=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, todocode);
			pstmt.setInt(2, usercode);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

	@Override
	public int getTodoCode(int usercode, String todo) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "SELECT\n"
					+ "	todocode\n"
					+ "FROM todos\n"
					+ "	where\n"
					+ "		usercode=? AND toDo=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, usercode);
			pstmt.setString(2, todo);
			rs = pstmt.executeQuery();
			
			rs.next();
			result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

	@Override
	public int updateTodobyBtn(int todocode, int importance, String state) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt =null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "update todos set importance=?, state=? where todocode=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, importance);
			pstmt.setString(2, state);
			pstmt.setInt(3, todocode);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}

}
