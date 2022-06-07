package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;

import db.DBConnectionMgr;
import entity.User;
import exception.DuplicateUsernameException;
import exception.PasswordMismatchException;
import exception.UserNotFoundException;

public class UserDaoImpl implements UserDao {
	private DBConnectionMgr pool = DBConnectionMgr.getInstance();
	
//	@Override
//	public int checkValidString(String string) {
//		int result=1;
//		
//		if(string.contains("\'") || string.contains("\"") || string.contains("=")) {
//			result=0;
//		}
//		return result;
//	}
	
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
				throw new DuplicateUsernameException();
				
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
		} catch (DuplicateUsernameException e) {
			System.out.println("username already taken");
			
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
			System.out.println("error in UDI");
		} catch (Exception e) {
			e.printStackTrace();
			
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
		String result = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			con = pool.getConnection();
			sb.append("select password from user where username=?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs == null) {
				throw new UserNotFoundException();
			}
			
			rs.next();
			result = rs.getString(1);
			
			if(result.equals(password)) {
				user = getUserByUsername(username);				
			} else {
				throw new PasswordMismatchException();
			}
			
		} catch (SQLException e) {
			System.out.println("trying sql injection?");
			
		} catch (UserNotFoundException e) {
			System.out.println("user not found");
			
		} catch (PasswordMismatchException e) {
			System.out.println("password no match");
			
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
		
		sb.append(" update_date = now() where username = ?");
		
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, newUser.getUsername()); // sql injection
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
		try {
			con=pool.getConnection();
			sql = "delete from user where usercode=? and username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user.getUsercode());
			pstmt.setString(2, user.getUsername());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			pool.freeConnection(con, pstmt);
			
		}
		return result;
	}

	@Override
	public int checkValidEmail(String email) {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "select count(email) from user where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			pool.freeConnection(con, pstmt, rs);
			
		}
		return result;
	}

	@Override
	public int createTempPassword(String username, String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
