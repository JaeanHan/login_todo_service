package entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
	private int usercode;
	private String name;
	private String email;
	private String username;
	private String password;
	private String role;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	
}
