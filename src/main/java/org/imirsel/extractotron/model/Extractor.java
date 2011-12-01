package org.imirsel.extractotron.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

@Entity
@Table(name = "extractor")
@Searchable
@XmlRootElement
public class Extractor extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525457543146548228L;
	
	private Long id;
	private String name;     //required
	private String commandLine;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 50, unique = false)
	@SearchableProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 256, unique = false)
	@SearchableProperty
	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	public String toString() {
		return "Extractor: [" + this.name +  " command line: " +"commandLine ]";
	}

	@Override
	public boolean equals(Object o) {
		 if (this == o) {
	            return true;
	        }
	        if (!(o instanceof User)) {
	            return false;
	        }

	        final Extractor extractor = (Extractor) o;

	        boolean val=!(name != null ? !name.equals(extractor.getName()) : extractor.getName() != null);
	        
	        return val;
	}

	@Override
	public int hashCode() {
		 return name.hashCode();
	}

}
