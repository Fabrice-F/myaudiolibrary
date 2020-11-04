package model;

import javax.persistence.*;


@Entity
@Table(name = "artist")
public  abstract class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ArtistId")
    private Integer id;

    private String Name;


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
