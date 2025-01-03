package com.example.springbootproject.controller;


import com.example.springbootproject.entity.Player;
import com.example.springbootproject.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
        1. @RestController Anotasyonu
        @RestController, bu sınıfın bir controller olduğunu ve HTTP isteklerini (GET, POST, PUT, DELETE vb.) işlediğini belirtir.
        @Controller ile aynı işlevi görür, ancak ek olarak metotların dönüş değerlerini otomatik olarak JSON formatına dönüştürür.

        @RequestMapping Anotasyonu
        Bu anotasyon, bu sınıfa ait tüm endpoint'lerin başlangıç yolunu tanımlar.
        Örneğin:
        Eğer sınıfın bir metodu @GetMapping(path = "/all") içeriyorsa, tam URL api/player/all olur.
        Bu, URL yapılandırmasını daha düzenli ve okunabilir hale getirir.
 */

@RestController
@RequestMapping(path = "api/player")
public class controller {

    private final service playerService;

    @Autowired
    public controller(service playerService){
        this.playerService=playerService;
    }

    @GetMapping
    public List<Player>  getPlayers(
            @RequestParam(required = false) String team ,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation

    ){
        if(team != null && position != null){
            return playerService.getPlayersByTeamAndPosition(team, position);
        }else if(team != null){
            return  playerService.getPlayersFromTeam(team);
        }else if(name != null){
            return playerService.getPlayerByName(name);
        }else if(position != null){
            return playerService.getPlayerByPos(position);
        }else if(nation != null){
            return playerService.getPlayersByNation(nation);
        }
        else{
            return playerService.getPalyers();
        }

    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);

         /*
        ResponseEntity, HTTP yanıtını daha detaylı bir şekilde yapılandırmanıza olanak tanır.
        Yanıt, iki parçadan oluşur:
        Body: createdPlayer, yani veri tabanına eklenen oyuncu nesnesi.
        Status Code: HttpStatus.CREATED (HTTP 201), başarılı bir şekilde yeni bir kaynak oluşturulduğunu belirtir.
         */
    }


    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player updatedPlayer) {
        Player resultPlayer = playerService.updatePlayer(updatedPlayer);
        if (resultPlayer != null) {
            return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }

}
