package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Le 02/12/2020 :
 *  Evaluation réalisé par Fabrice FERRERE pour le module JAVA Rest
 *
 *
 *
 *
 *
 *  ⚠ La base de donnée utilisée dans ce tp est celle fournit dans le dossier contenant le "front-end".
 *  Elle présente des différences notemment au niveau des clés primaire de la table album et artist.
 *  Dans celle dans ce tp elles sont nommées : "id" dans les 2 tables au lieu de "AlbumId" et "ArtistId" dans la
 *  précèdente fournit.
 *
 *
 * Autre souci: Lors de certaines gestion d'erreur ( exemple: lorqu'un artiste existe déja ) le message complet
 * apparait avec mon message personnalisé :
 *
 * Erreur lors de la sauvegarde ! Error: Ember Data Request
 * POST http://localhost:5366/artists returned a 409 Payload
 * (Empty Content-Type) "L'artiste que vous souhaitez ajouté existe déjà " <= message personnalisé
 *
 *
 * Ne sachant pas pourquoi celui-ci apparait aussi complet et non pas seulement avec mon message personnalisé
 * je n'ai pas trouvé de solution.
 * */




@RestController
@RequestMapping(value = "artists")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;


    /**
     * Appeler quand on souhbaite le détais d'un artist.
     * @param id de l'artiste que l'on souhaite avoir le détails.
     * @return un optionnal artist
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    public Optional<Artist> getArtistById(@PathVariable(value = "id") Integer id){



        // erreur de conversion géré par MethodArgumentTypeMismatchException dans globalException.

        Optional<Artist> optionalArtist = artistRepository.findById(id);

        // si aucun artiste n'as été trouvé :
        if (optionalArtist.isEmpty())
            throw new EntityNotFoundException("L'artiste avec l'id " + id + " n'existe pas");

        return artistRepository.findById(id);
    }


    /**
     * Appeler lors de la recherche par nom d'un artist
     * @param name  nom de l'artiste casse insensitive
     * @param page  numéro de la page
     * @param size  nombre de résultat par page
     * @param sortProperty  recherche par "nom" ou "autre..."
     * @param sortDirection type de tri ascendent ou descendant
     * @return une page contenant des artistes
     */
    @RequestMapping(params = {"name"},method = RequestMethod.GET ,produces = "application/json")
    public Page<Artist> searchArtistByName(
            @RequestParam("name") String name,
            @RequestParam(value="page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sortProperty" , defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection" ) Sort.Direction sortDirection
    )
    {

        //Si sortDirection à une mauvaise valeur MethodArgumentTypeMismatchException levée
        // Observation :  Si la propriété name n'existe pas ou est vide cela ne renvoi pas d'erreur donc pas
        // d'erreur à gérer

        // Limite le nombre de résultat possible par page
        if (size<10|| size>50)
            throw new IllegalArgumentException("L'argument size n'est pas situé entre les bornes 10 et 50");

        // si on tente d'obtenir une page inférieur à 0.
        if (page<0)
            throw new IllegalArgumentException("Le numéro de la page est incorrect");

        PageRequest pageRequest = PageRequest.of(page,size,sortDirection,sortProperty);
        return artistRepository.findAllByNameIgnoreCaseContaining(name,pageRequest);
    }
    /**
     * Appeler lorsque l'on veut la liste de tous les artistes
     * @param page  numéro de la page
     * @param size  nombre de résultat par page
     * @param value  recherche par "nom" ou "autre..."
     * @param sortDirection type de tri ascendent ou descendant
     * @return une page contenant tous les artistes paginé
     */
    @RequestMapping( method = RequestMethod.GET,produces = "application/json")
    public Page<Artist> getAllArtis(
            @RequestParam(value="page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sortProperty" ,defaultValue = "name") String value,
            @RequestParam(value = "sortDirection" ) Sort.Direction sortDirection
    ){
        //Si sortDirection à une mauvaise valeur MethodArgumentTypeMismatchException levée

        if (size<10|| size>50)
            throw new IllegalArgumentException("L'argument size n'est pas situé entre les bornes 10 et 50");

        if (page<0)
            throw new IllegalArgumentException("Le numéro de la page est incorrect");


        PageRequest pageRequest = PageRequest.of(page,size,sortDirection,value);
        return artistRepository.findAll(pageRequest);
    }


    /**
     * Appeler lortque l'on souhaite créer un artiste
     * @param artist reçu par le formulaire
     * @return l'artiste enregistrer dans la bdd
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist createArtist(@RequestBody Artist artist)
    {


        if(artist.getName().isEmpty() || artist.getName().isBlank())
            throw new IllegalArgumentException("Le nom de l'artiste n'est pas renseigné");

        if(artistRepository.existsByNameIgnoreCase(artist.getName()))
            throw new EntityExistsException("L'artiste que vous souhaitez ajouté existe déjà");

        // ne sachant pas si on autorisait les artists avec seulement des chiffres
        // j'ai crée l'exception mais elle n'est pas implémenté.
        // A décommenté si on veut y mettre en place.
        //if (isNumeric(artist.getName()))
            //throw new IllegalArgumentException("Le nom de l'artiste ne contient que des nombres");

        Artist result =  artistRepository.save(artist);
        return result;
    }


    /**
     * Appeler lorque l'on met l'artist à jour
     * @param id de l'artist
     * @param artist l'artist reçu par le front
     * @return l'artist sauvegarder
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE,value = "/{id}")
    public Artist updateArtist(@PathVariable Integer id,@RequestBody Artist artist)
    {
        //Traitement erreur convertion id grace à MethodArgumentTypeMismatchException dans globalException
        // erreur si on n'entre pas un nom ou des espaces blanc.

        if (!artistRepository.existsById(id))
            throw new IllegalArgumentException("L'artiste avec l'id : "+ id + " n'existe pas");

        if(artist.getName().isEmpty() || artist.getName().isBlank())
            throw new IllegalArgumentException("Le nom de l'artiste n'est pas renseigné");

        Artist result =  artistRepository.save(artist);
        return result;
    }


    /**
     * appeler lorsque l'on supprime un artiste
     * @param id de l'artist à supprimer
     */
    @DeleteMapping(produces = "application/json", value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArtiste(@PathVariable("id") Integer id)
    {

        //Traitement erreur convertion id grace à MethodArgumentTypeMismatchException dans globalException

        if (id<1)
            throw new IllegalArgumentException("L'id de l'artiste doit être supérieur à 0" );

        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isEmpty())
            throw new EntityNotFoundException("L'artist avec l'id " + id + " n'as pas été trouvé");

        artistRepository.delete(artist.get());
    }


    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
