package org.imirsel.extractotron.model;

import java.io.Serializable;
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

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

@Entity
@Table(name = "collection")
@Searchable
@XmlRootElement
public class SongCollection extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6318370670712092010L;

	
	private Long id;
	private String name;
	private String description;
	private Boolean userDefined = Boolean.TRUE;
    private Set<Song> songs = new HashSet<Song>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	 @Column(nullable = false, length = 150, unique = false)
	 @SearchableProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 256, unique = false)
	@SearchableProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	 @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	            name = "collection_song",
	            joinColumns = { @JoinColumn(name = "collection_id") },
	            inverseJoinColumns = @JoinColumn(name = "song_id")
	    )
	public Set<Song> getSongs() {
	    return songs;
	}
	

	public void setUserDefined(Boolean userDefined) {
		this.userDefined = userDefined;
	}

	public Boolean getUserDefined() {
		return userDefined;
	}

	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "collection ["+this.name+"]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof SongCollection)) {
            return false;
        }

        final SongCollection collection = (SongCollection) o;

        return !(collection != null ? !collection.equals(collection.getName()) : collection.getName() != null);
	}

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}


}
