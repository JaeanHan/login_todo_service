package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBConnectionMgr;
import entity.User;
import exception.DuplicateUsernameException;
import exception.PasswordMismatchException;
import exception.UserNotFoundException;
import request.AutoSQLRequestDto;
import respond.AutoSQLRespondDto;

public class UserDaoImpl implements UserDao {
	private final DBConnectionMgr pool = DBConnectionMgr.getInstance();
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public UserDaoImpl() {
	
	}
	
	public UserDaoImpl(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	@Override
	public int checkUsernameDuplicate(String username) throws DuplicateUsernameException {
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "select count(username) from user where username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		if(result >0) {
			throw new DuplicateUsernameException();
		}
		return result;
	}
	
	@Override
	public int SignUp(User user) {
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			result = checkUsernameDuplicate(user.getUsername());
			
			con = pool.getConnection();
			sb.append("insert into user values(0, ?, ?, ?, ?, ?, now(), now())");
			pstmt = con.prepareStatement(sb.toString());
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getRole());
			
			result = pstmt.executeUpdate();
		
		} catch (DuplicateUsernameException e) {			
				try {
					req.setAttribute("reason", "sorry but username " + user.getUsername() + " is already taken!");
					req.getRequestDispatcher("/issue").forward(req, resp);
					req.removeAttribute("reason");
					
				} catch (Exception e1) {
					System.out.println("sign up says hi");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
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
		ResultSet rs = null;
		User user = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sb.append("SELECT\n"
					+ "	COUNT(username),\n"
					+ "	case when PASSWORD = ? then 1 ELSE 0\n"
					+ "	END AS pwd\n"
					+ "FROM user\n"
					+ "WHERE username=?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			rs = pstmt.executeQuery();
			rs.next();
			
			result = rs.getInt(1) + rs.getInt(2);
			
			if(result == 0) {
				throw new UserNotFoundException();
			}
			
			if(result == 1) {
				throw new PasswordMismatchException();
			}
			
			user = getUserByUsername(username); //private?
			
		} catch (SQLException e) {
			System.out.println("what happened?");
			
		} catch (UserNotFoundException e) {
			try {
				req.setAttribute("reason", "your username is not found create new account!");
				req.getRequestDispatcher("/issue").forward(req, resp);
				req.removeAttribute("reason");
			} catch (Exception e1) {
				System.out.println("signin says hi");
			}
			
		} catch (PasswordMismatchException e) {
			System.out.println("password no match");
			
			try {
				req.setAttribute("reason", "your password is incorrect!");
				req.getRequestDispatcher("/issue").forward(req, resp);
				req.removeAttribute("reason");
			} catch (Exception e1) {
				System.out.println("signin says hello");
			}
			
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
		AutoSQLRequestDto req = AutoSQLRequestDto.builder()
									.sql(sql)
									.username(user.getUsername())
									.usercode(user.getUsercode())
									.mode(0)
									.build();
			
		result = (int) autoSQL(req).getResult();
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
				+ "	COUNT(email)\n"
				+ "FROM user\n"
				+ "	WHERE username=? AND email=?";
		ResultSet rs = null;
		int selectResult=0;
		int updateResult=0;
		AutoSQLRequestDto req = AutoSQLRequestDto.builder()
									.sql(sql)
									.username(username)
									.email(email)
									.newPassword(newPassword)
									.usercode(-1)
									.mode(1)
									.build();
						
		try {
			rs = (ResultSet) autoSQL(req).getResult();
			rs.next();
			selectResult = rs.getInt(1) + rs.getInt(2);
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(selectResult == 2) {
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
		AutoSQLRequestDto req = AutoSQLRequestDto.builder()
									.username(username)
									.newPassword(newPassword)
									.sql(sql)
									.mode(0)
									.usercode(-1)
									.build();
				
		result = (int) autoSQL(req).getResult();
		return result;
	}

	//1 select mode
	//0 insert, update, delete mode
	private AutoSQLRespondDto<?> autoSQL(AutoSQLRequestDto req) {
		AutoSQLRespondDto<ResultSet> respond = null;
		AutoSQLRespondDto<Integer> respond2 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		int usercode = req.getUsercode();
				
		try {
			con=pool.getConnection();
			pstmt=con.prepareStatement(req.getSql());
			
			if(req.getMode()==1) {
				pstmt.setString(1, req.getUsername());
				pstmt.setString(2, req.getEmail());
				rs = pstmt.executeQuery();
				respond = new AutoSQLRespondDto<ResultSet>(rs);
				
			} else {
				if(usercode == -1) {
					pstmt.setString(1, req.getNewPassword());
				} else {
					pstmt.setInt(1, usercode);
				}
				
				pstmt.setString(2, req.getUsername());
				result = pstmt.executeUpdate();
				respond2 = new AutoSQLRespondDto<Integer>(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return req.getMode()==1 ? respond : respond2;
	}
}
