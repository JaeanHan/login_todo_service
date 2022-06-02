package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnectionMgr;
import entity.User;

public class UserDaoImpl implements UserDao {
	private DBConnectionMgr pool = DBConnectionMgr.getInstance();
	
	@Override
	public int SignUp(User user) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sb.append("select count(username) from user where username=?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, user.getUsername());
			result = pstmt.executeUpdate();
			
			if(result > 0) { // if id duplicated
				pool.freeConnection(con, pstmt);
				return 0; 
			} else {
				sb.setLength(0); // fastest
				sb.append("insert into user values(0, ?, ?, ?, ?, now(), now())");
				
				pstmt.close();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getEmail());
				pstmt.setString(3, user.getUsername());
				pstmt.setString(4, user.getPassword());
				
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	@Override
	public User getUserByUsername(String username) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			con = pool.getConnection();
			sb.append("select * from user where username=?");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, username);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			user = User.builder()
					.usercode(rs.getInt(1))
					.name(rs.getString(2))
					.email(rs.getString(3))
					.username(rs.getString(4))
					.password(rs.getString(5))
					.create_date(rs.getTimestamp(6).toLocalDateTime())
					.update_date(rs.getTimestamp(7).toLocalDateTime())
					.build();
			
		} catch (SQLException e) {
			System.out.println("idk maybe sql?");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("IDK but happened");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return user;
	}

	@Override
	public User SignIn(String username, String password) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0;
		ResultSet rs = null;
		User user = null;
		
		try {
			con = pool.getConnection();
			sb.append("select count(name) from user where username=? and password=?");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			result = rs.getInt(1);
			
			if(result == 1) {
				user = getUserByUsername(username);
			}
			
		} catch (SQLException e) {
			pool.freeConnection(con, pstmt, rs);
			System.out.println("booboo");
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return user;
	}	
	
	@Override
	public int updateProfile(User newUser) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		User existingUser = null;
		int result = 0;
		
		existingUser = getUserByUsername(newUser.getUsername());
				
		sb.append("update user set");
		
			if(!newUser.getName().equals(existingUser.getName())) {
				sb.append(" name = " + "\"" + newUser.getName() + "\",");	
			}
			
			if(!newUser.getEmail().equals(existingUser.getEmail())) {
				sb.append(" email = " + "\""+ newUser.getEmail() + "\",");
			}
			
			if(!newUser.getPassword().equals(existingUser.getPassword())) {
				sb.append(" password = " + "\"" + newUser.getPassword() + "\",");
			}
		
		sb.append(" update_date = now() where username = " + "\"" + newUser.getUsername() +"\"");
		
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return result;
	}

	@Override
	public int deleteUserByUsername(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
//		System.out.println(user.getUsername());
		try {
			con=pool.getConnection();
			sql = "delete from user where username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int checkValidEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkValidUsername(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createTempPassword(String username, String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
