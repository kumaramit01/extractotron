package org.imirsel.extractotron.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
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
	private Set<Extractor> extractors = new HashSet<Extractor>();

	private Set<ExecutionContext> executionContexts = new HashSet<ExecutionContext>();
	
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
	 
	 
	 @Column(nullable = false, length = 256, unique = false)
	 @SearchableProperty
	 public String getCommandLine() {
	   return commandLine;
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

	 @Transient
	 public List<LabelValue> getSongCollectionList() {
		 List<LabelValue> projectCollections = new ArrayList<LabelValue>();

		 if (this.songCollections != null) {
			 for (SongCollection sc : songCollections) {
				 projectCollections.add(new LabelValue(sc.getName(), sc.getName()));
			 }
		 }

		 return projectCollections;
	 }

	public void setId(Long id) {
	    this.id = id;
	}


	 @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	            name = "project_extractor",
	            joinColumns = { @JoinColumn(name = "project_id") },
	            inverseJoinColumns = @JoinColumn(name = "extractor_id")
	  )
	public Set<Extractor> getExtractors() {
		return extractors;
	}


	 @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	    @JoinTable(
	            name = "project_execution_context",
	            joinColumns = { @JoinColumn(name = "project_id") },
	            inverseJoinColumns = @JoinColumn(name = "executioncontext_id")
	           
	  )
	public Set<ExecutionContext> getExecutionContexts() {
		return executionContexts;
	}



	
	public void setExtractors(Set<Extractor> extractors) {
		this.extractors = extractors;
	}



	public void setExecutionContexts(Set<ExecutionContext> executionContexts) {
		this.executionContexts = executionContexts;
	}
	
	public void addCollection(SongCollection sc) {
		this.getSongCollections().add(sc);
	}

	public void addExecutionContext(ExecutionContext ec){
		this.getExecutionContexts().add(ec);
	}




	@Transient
	 public Extractor getExtractor() {
		return extractors.iterator().next();
     }
	
	@Transient
	public void addExtractor(Extractor extractor) {
		this.getExtractors().add(extractor);
	}
	
	@Transient
	 public List<LabelValue> getExtractorsList(){
		 List<LabelValue> collections = new ArrayList<LabelValue>();
		 if (this.extractors != null) {
			 for (Extractor sc : extractors) {
				 collections.add(new LabelValue(sc.getName(), sc.getCommandLine()));
			 }
		 }
		 return collections;
	 }
	
	@Transient
	public String getFeature(){
		if(this.getCommandLine() == null){
			return "";
		}else{
			String[] tokens = this.getCommandLine().split("\\s+");
			boolean found=Boolean.FALSE;
			String feature = "";
			for(String token:tokens){
				if(found){
					feature = token;
					break;
				}
				if(token.equalsIgnoreCase("-fe")){
					found = Boolean.TRUE;
				}
			}
			return feature;
		}
		
	}


	@Override
	public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
         .append("name", this.name)
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
