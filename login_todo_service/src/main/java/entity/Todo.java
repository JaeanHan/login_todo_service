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
public class Todo {
	private int todocode;
	private int usercode;
	private String todo;
	private String state;
	private int importance;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	
}
