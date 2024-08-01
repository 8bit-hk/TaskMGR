package jp.eightbit.exam.model;

public class TaskSearchQuery {

	private int priorityNum;
	private String from;
	private String to;
	private String taskName;
	private String repName;
	
	public int getPriorityNum() {
		return priorityNum;
	}
	public void setPriorityNum(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getRepName() {
		return repName;
	}
	public void setRepName(String repName) {
		this.repName = repName;
	}
	@Override
	public String toString() {
		return "TaskSearchQuery [priorityNum=" + priorityNum + ", from=" + from + ", to=" + to + ", taskName="
				+ taskName + ", repName=" + repName + "]";
	}

	
	
	
}
