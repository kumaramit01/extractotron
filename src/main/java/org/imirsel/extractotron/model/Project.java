package org.imirsel.extractotron.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

@Entity
@Table(name = "project")
@Searchable
@XmlRootElement
public class Project extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2529591992936419137L;
	
	
	private Long id;
	private String name;     //required
	private String description;  
	private String commandLine; // required
	private Date timeStarted;   
	private Date timeEnded;
	private String status;
	
	private Set<SongCollection> songCollections = new HashSet<SongCollection>();
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @SearchableId
	  public Long getId() {
	     return id;
	  }

	  
	 @Column(nullable = false, length = 150, unique = false)
	 @SearchableProperty
	 public String getName() {
	   return name;
	 }
	 
	  
	 @Column(nullable = false, length = 256, unique = false)
	 @SearchableProperty
	 public String getDescription() {
	   return description;
	 }
	 
	 @Column(nullable = false, length = 16, unique = false)
	 @SearchableProperty
	 public String getStatus() {
	   return status;
	 }
	 
	 @Column(nullable = false, length = 256, unique = false)
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
	 


	 
	public void setName(String name) {
		this.name = name;
	}


	public void setDescription(String description) {
		this.description = description;
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


	public void setStatus(String status) {
		this.status = status;
	}


	
	public void setSongCollections(Set<SongCollection> songCollections) {
		this.songCollections = songCollections;
	}

	 @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	            name = "project_collection",
	            joinColumns = { @JoinColumn(name = "project_id") },
	            inverseJoinColumns = @JoinColumn(name = "collection_id")
	  )
	public Set<SongCollection> getSongCollections() {
		return songCollections;
	}


	public void setId(Long id) {
	    this.id = id;
	}


	@Override
	public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
         .append("name", this.name)
         .append("status", this.status)
         .append("commandLine", this.commandLine);
		 return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		 if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Project)) {
	            return false;
	        }

	        final Project project = (Project) o;

	        return !(project != null ? !project.equals(project.getName()) : project.getName() != null);

	}

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}

}