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

import javax.persistence.EntityNotFoundException;
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

        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isEmpty())
            throw new EntityNotFoundException("L'artiste avec l'id " + id + " n'as pas été trouvé ");
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
        //TODO: SI NAME EXISTE OU PAS VIDE
        if (size<10|| size>50)
            throw new IllegalArgumentException("L'argument size n'est pas situé entre les bornes 10 et 50");
        // TODO: TRAITEMENT PAGE <1
        // TODO: TRAITEMENT SI INTEGER RECOIT UN UN AUTRE TYPE
        // TODO: TRAITEMENT SI SORT DIRECTION INVALIDE
        // TODO: TRAITEMENT SI SORT PROPERTY NON RENSEIGNER OU INCORRECT ( valeur par défaut)
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
        if (size<10|| size>50)
            throw new IllegalArgumentException("L'argument size n'est pas situé entre les bornes 10 et 50");
        // TODO: TRAITEMENT PAGE <1
        // TODO: TRAITEMENT SI INTEGER RECOIT UN UN AUTRE TYPE
        // TODO: TRAITEMENT SI SORT DIRECTION INVALIDE
        // TODO: TRAITEMENT SI SORT PROPERTY NON RENSEIGNER OU INCORRECT ( valeur par défaut)


        PageRequest pageRequest = PageRequest.of(page,size,sortDirection,value);
        return artistRepository.findAll(pageRequest);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist createArtist(@RequestBody Artist artist)
    {
        // TODO: TRAITEMENT SI ARTISTE N'EST PAS NULL
        // TODO: TRAITEMENT SI ARTISTE EXISTE DEJA
        // TODO: TRAITEMENT SI MANQUE DES INFORMATIONS A ARTISTE (voir required décorateur)
        Artist result =  artistRepository.save(artist);
        return result;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    public Artist updateArtist(@PathVariable Integer id,@RequestBody Artist artist)
    {

        // TODO: TRAITEMENT SI ID EST SUPERIEUR A 0 ET SI BONNE CONVERSION
        // TODO: TRAITEMENT SI ARTIST EXISTE
        // TODO: TRAITEMENT SI ARTIST N'EST PAS VIDE
        // TODO: TRAITEMENT SI MANQUE DES INFORMATIONS A ARTISTE (voir required décorateur)
        Artist result =  artistRepository.save(artist);
        return result;
    }

    @DeleteMapping(produces = "application/json", value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArtiste(@PathVariable("id") Integer id)
    {
        // TODO: TRAITEMENT SI ID EST SUPERIEUR A 0 ET SI BONNE CONVERSION
        // TODO: TRAITEMENT SI ARTIST EXISTE
        Artist artist = artistRepository.findById(id).get();
        artistRepository.delete(artist);
    }

}
