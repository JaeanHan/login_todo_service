package request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AutoSQLRequestDto {
	private String sql;
	private String username;
	private String email;
	private String newPassword;
	private int usercode;
	private int mode;
}
