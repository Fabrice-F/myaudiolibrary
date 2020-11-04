package model;


import javax.persistence.*;

@Entity
@Table(name = "album")
public abstract class Album {

    @Id
    @Column(name = "AlbumId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;


    @OneToOne
    @JoinColumn(name = "ArtistId",nullable = false)
    private Artist ArtistId;
}
