package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "albums")
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces =
            MediaType.APPLICATION_JSON_VALUE)
    public Album ajoutAlbum(@RequestBody Album album){
        Album result = albumRepository.save(album);
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE,produces =
            MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ajoutAlbum(@PathVariable("id") Integer id){
        albumRepository.deleteById(id);
    }
}
