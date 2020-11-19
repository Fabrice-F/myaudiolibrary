package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "artists")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    public Optional<Artist> searchArtistById(@PathVariable(value = "id") Integer id){
        return artistRepository.findById(id);
    }

    @RequestMapping(params = {"name"},method = RequestMethod.GET ,produces = "application/json")
    public Page<Artist> searchArtistByName(
            @RequestParam("name") String name,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sortProperty") String sortProperty,
            @RequestParam(value = "sortDirection" ) Sort.Direction sortDirection
    )
    {
        PageRequest pageRequest = PageRequest.of(page,size,sortDirection,sortProperty);
        return artistRepository.findAllByNameIgnoreCaseContaining(name,pageRequest);
    }

    @RequestMapping( method = RequestMethod.GET,produces = "application/json")
    public Page<Artist> searchAllArtistWithPagination(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sortProperty") String value,
            @RequestParam(value = "sortDirection" ) Sort.Direction sortDirection
    ){
        PageRequest pageRequest = PageRequest.of(page,size,sortDirection,value);
        return artistRepository.findAll(pageRequest);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist createArtist(@RequestBody Artist artist)
    {
        Artist result =  artistRepository.save(artist);
        return result;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    public Artist updateArtist(@PathVariable Integer id,@RequestBody Artist artist)
    {
        System.out.println(id);
        System.out.println(artist.getName());

        Artist result =  artistRepository.save(artist);
        return result;
    }

    @DeleteMapping(produces = "application/json", value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArtiste(@PathVariable("id") Integer id)
    {

        Artist artist = artistRepository.findById(id).get();
        artistRepository.delete(artist);
    }

}
