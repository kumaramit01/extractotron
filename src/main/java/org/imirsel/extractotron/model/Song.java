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
@Table(name = "song")
@Searchable
@XmlRootElement
public class Song extends BaseObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7697188076713702102L;

	public void setName(String name) {
		this.name = name;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	 public void setId(Long id) {
	        this.id = id;
	 }
	  
	private Long id;
	private String name;
	private String location;
	
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
	 public String getLocation() {
	   return location;
	 }


	@Override
	public String toString() {
		return "song["+name+"]";
	}


	@Override
	public boolean equals(Object o) {
    if (this == o) {
            return true;
    }
    if (!(o instanceof Song)) {
        return false;
    }
    final Song song = (Song) o;
    return !(song != null ? !song.equals(song.getLocation()) : song.getLocation() != null);
	}


	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	 

}
