package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController
@RequestMapping(value = "albums")
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;


    /**
     * Appeler quand on souhaite ajouter un album à un artiste
     * @param album l'album à ajouter
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces =
            MediaType.APPLICATION_JSON_VALUE)
    public Album ajoutAlbum(@RequestBody Album album){


        // erreur si l'on souahite ajouter un album qui existe déja dans la bdd

        if (albumRepository.existsByTitleIgnoreCase(album.getTitle()))
            throw new EntityExistsException("L'album avec le nom: \"" + album.getTitle() + "\" existe déja");

        if(album.getTitle().isEmpty() || album.getTitle().isBlank())
            throw new IllegalArgumentException("Le nom de l'album n'est pas renseigné");

        Album result = albumRepository.save(album);
        return result;
    }


    /**
     * appeler quand on supprime un artiste
     * @param id
     */
    @RequestMapping(method = RequestMethod.DELETE,produces =
            MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable("id") Integer id){

        if (id<1)
            throw new IllegalArgumentException("L'id de l'album doit être égal ou supérieur à 1" );

        Optional<Album> album = albumRepository.findById(id);

        if(album.isEmpty())
            throw new EntityNotFoundException("L'album avec l'id " + id + " n'as pas été trouvé");

        albumRepository.deleteById(id);
    }
}
