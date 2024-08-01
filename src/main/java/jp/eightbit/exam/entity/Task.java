package jp.eightbit.exam.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "priority")
	private TaskPriority priority;
	
	@Column(name = "due_time")
	private LocalDateTime dueTime;
	
	@OneToOne
	@JoinColumn(name = "status")
	private TaskState status;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public TaskPriority getPriority() { return priority; }

	public void setPriority(TaskPriority priority) { this.priority = priority; }


	public LocalDateTime getDueTime() { return dueTime;	}

	public void setDueTime(LocalDateTime dueTime) { this.dueTime = dueTime;	}

	public TaskState getStatus() { return status; }

	public void setStatus(TaskState status) { this.status = status; }

	public User getUser() { return user; }

	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", priority=" + priority + ", dueTime=" + dueTime + ", status="
				+ status + ", user=" + user + "]";
	}



	
}
