package org.imirsel.extractotron.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

@Entity
@Table(name = "execution_context")
@Searchable
@XmlRootElement
public class ExecutionContext extends BaseObject implements Serializable {


	private static final long serialVersionUID = -8286811657255065928L;

	private Long id;
	private Long pid;
	private String name;
	private Date timeCreated;
	private Date timeStarted;
	private Date timeEnded;
	private Date timePolled;
	private String status;
	private String workingDirectory;
	private String commandLine;
	private String resultFile;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	public Long getId() {
		return id;
	}

	@Column(nullable = false, unique = false, columnDefinition = " varchar(16) default 'CREATED' ")
	@SearchableProperty
	public String getStatus() {
		return status;
	}

	@Column(nullable = false, length = 1024, unique = false)
	@SearchableProperty
	public String getCommandLine() {
		return commandLine;
	}

	@Column(nullable = true, unique = false)
	@SearchableProperty
	public Date getTimeStarted() {
		return timeStarted;
	}

	@Column(nullable = true, unique = false)
	@SearchableProperty
	public Date getTimeEnded() {
		return timeEnded;
	}
	
	@Column(nullable = true, unique = false)
	@SearchableProperty
	public Date getTimePolled() {
		return timePolled;
	}
	
	@Column(nullable = false, length = 1024, unique = false, columnDefinition = " varchar(1024) ")
	@SearchableProperty
	public String getWorkingDirectory() {
		return workingDirectory;
	}

	@Column(nullable = false, length = 1024, unique = false, columnDefinition = " varchar(1024) ")
	@SearchableProperty
	public String getResultFile() {
		return resultFile;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public void setTimeStarted(Date timeStarted) {
		this.timeStarted = timeStarted;
	}

	public void setTimeEnded(Date timeEnded) {
		this.timeEnded = timeEnded;
	}

	public void setTimePolled(Date timePolled) {
		this.timePolled = timePolled;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}





	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(nullable = false, unique = false, columnDefinition=" BIGINT Default -1")
	public Long getPid() {
		return pid;
	}

	@Column(nullable = false, unique = false)
	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, unique = false)
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("status", this.status)
				.append("commandLine", this.commandLine);
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ExecutionContext)) {
			return false;
		}
		final ExecutionContext ec = (ExecutionContext) o;
		if (ec != null) {
			if (ec.getCommandLine().equals(this.commandLine)
					&& ec.timeCreated.equals(this.timeCreated)) {
					if(ec.pid == this.pid){
						return true;
					}else{
						return false;
					}
				
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hcode = 0;
		if (commandLine != null) {
			hcode = commandLine.hashCode();
		}
		if (this.timeStarted != null) {
			hcode = hcode + timeStarted.hashCode();
		}
		if(this.pid != null){
			hcode = hcode + this.pid.hashCode();
		}
		return hcode;
	}

	@Transient
	public boolean canBeStopped() {
		//		String[] phases = new String[]{"CREATED","RUNNING","ABORTED","ABORT_REQUESTED","FINISHED"};
		if(this.getStatus().equalsIgnoreCase("RUNNING") || this.getStatus().equalsIgnoreCase("ABORT_REQUESTED")){
			return false;
		}
		return true;
	}

}
