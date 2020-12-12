
 Le 02/12/2020 :
 Evaluation réalisé par Fabrice FERRERE pour le module JAVA Rest
 

  ⚠ La base de donnée utilisée dans ce tp est celle fournit dans le dossier contenant le "front-end".
  Elle présente des différences notemment au niveau des clés primaire de la table album et artist.
  Dans celle dans ce tp elles sont nommées : "id" dans les 2 tables au lieu de "AlbumId" et "ArtistId" dans la
  précèdente fournit.


 Autre souci: Lors de certaines gestion d'erreur ( exemple: lorqu'un artiste existe déja ) le message complet
 apparait avec mon message personnalisé :

 Erreur lors de la sauvegarde ! Error: Ember Data Request
 POST http://localhost:5366/artists returned a 409 Payload
 (Empty Content-Type) "L'artiste que vous souhaitez ajouté existe déjà " <= message personnalisé

 Ne sachant pas pourquoi celui-ci apparait aussi complet et non pas seulement avec mon message personnalisé
 je n'ai pas trouvé de solution.
 
