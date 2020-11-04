package model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "artist")
public  abstract class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ArtistId")
    private Integer id;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums;


    private String Name;


    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
