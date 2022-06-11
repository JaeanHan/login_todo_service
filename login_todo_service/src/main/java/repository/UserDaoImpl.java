package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import db.DBConnectionMgr;
import entity.User;
import exception.DuplicateUsernameException;
import exception.PasswordMismatchException;
import exception.UserNotFoundException;
import respond.RespondDto;

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
				sb.append("insert into user values(0, ?, ?, ?, ?, ?, now(), now())");
				
				pstmt.close();
				pstmt = con.prepareStatement(sb.toString());
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getEmail());
				pstmt.setString(3, user.getUsername());
				pstmt.setString(4, user.getPassword());
				pstmt.setString(5, user.getRole());				
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
					.role(rs.getString(6))
					.create_date(rs.getTimestamp(7).toLocalDateTime())
					.update_date(rs.getTimestamp(8).toLocalDateTime())
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
			System.out.println("wow");
			
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
		String sql = "delete from user where usercode=? and username=?";
		int result = 0;
		result = (int) 
				autoSQL(sql, user.getUsername(), 
						Integer.toString(user.getUsercode()), 
						0).getResult();

		return result;
	}

	@Override
	public int checkValidEmail(String email) {
		int result = 0;
//		"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
//		"^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
		return result;
	}

	@Override
	public int resetPassword(String username, String email, String newPassword) {
		String sql ="SELECT\n"
				+ "	COUNT(username),\n"
				+ "	COUNT(email),\n"
				+ "FROM user\n"
				+ "	WHERE username=? AND email=?";
		ResultSet rs = null;
		int selectResult=0;
		int updateResult=0;
		
		try {
			rs = (ResultSet) autoSQL(sql, username, email, 1).getResult();
			rs.next();
			selectResult = rs.getInt(1) + rs.getInt(2) + rs.getInt(3);
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(selectResult == 3) {
			updateResult=updatePassword(username, newPassword);
		}
		return updateResult;
	}
	
	private int updatePassword(String username, String newPassword) {
		String sql = "UPDATE\n"
				+ "	user\n"
				+ "SET\n"
				+ "	PASSWORD=?\n"
				+ "WHERE\n"
				+ "	username=?";
		int result = 0;
		result = (int) autoSQL(sql, username, newPassword, 0).getResult();
		
		return 0;
	}

	//1 select mode
	//0 insert, update, delete mode
	private RespondDto<?> autoSQL(String sql, String username, String value, int mode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		int usercode = 0;
		RespondDto<ResultSet> respond = null;
		RespondDto<Integer> respond2 = null;
		
		Lambda isNumeric = (check) -> {
			Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); // "^[0-9]*$"
			
			if (check == null) {
		        return false; 
		    }
		    return pattern.matcher(check).matches();
		};
		
		if(isNumeric.isTrue(value)) {
			usercode = Integer.parseInt(value);
		}
		
		try {
			con=pool.getConnection();
			pstmt=con.prepareStatement(sql);
			
			if(mode==1) {
				pstmt.setString(1, username);
				pstmt.setString(2, value);
				rs = pstmt.executeQuery();
				respond = new RespondDto<ResultSet>(rs);
				
			} else {
				
				if(usercode == 0) {
					pstmt.setString(1, value);					
				} else {
					pstmt.setInt(1, usercode);
				}
				
				pstmt.setString(2, username);
				result = pstmt.executeUpdate();
				respond2 = new RespondDto<Integer>(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(mode==1) {
				pool.freeConnection(con, pstmt, rs);				
			} else {
				pool.freeConnection(con, pstmt);
			}
		}
		
		return mode==1? respond : respond2;
	}

}
