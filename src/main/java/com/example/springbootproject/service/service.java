package com.example.springbootproject.service;


import com.example.springbootproject.entity.Player;
import com.example.springbootproject.repository.repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/*
   @Component anotasyonu, bu sınıfın bir Spring Bean olduğunu belirtir. Yani Spring, bu sınıfı Spring konteynerine ekler ve yönetmeye başlar.
  Bu sınıfın, başka sınıflar tarafından kullanılabilmesi için Spring tarafından otomatik olarak yönetilmesi gereki
* */
@Component
public class service {

    private final repository playerRepository; //playerRepository, veri tabanına erişim sağlayacak ve Player nesneleri üzerinde işlem yapacak olan bir bağımlılıktır.

    /*

        @Autowired, Spring'in bağımlılık enjeksiyonunu sağlamak için kullanılır.
        Yani, service sınıfı oluşturulduğunda, Spring otomatik olarak playerRepository'yi sağlar.
        Bu, Spring'in constructor injection yöntemi ile bağımlılığı sağlayacağı anlamına gelir.
         Spring, service sınıfı oluşturulurken repository bean'ini doğru şekilde inject eder.
     */

    @Autowired
    public service(repository playerRepository){
        this.playerRepository = playerRepository;

        /*
        Bu constructor, playerRepository'yi almak için kullanılır.
        Spring, repository bağımlılığını otomatik olarak sağlar ve bu constructor'a geçirir.
         */

    }

    public List<Player> getPalyers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName){

        return playerRepository.findAll().stream().filter(player -> teamName.equals(player.getTeam())).collect(Collectors.toList());

        /*
        Bu metot, teamName parametresine göre oyuncuları filtreler.
        playerRepository.findAll() ile tüm oyuncuları alır, ardından stream() yöntemiyle bir akış (stream) oluşturur.
        filter() metodu, her bir oyuncunun takım adını kontrol eder. Eğer oyuncunun takımı, teamName parametresiyle eşleşiyorsa, o oyuncu akışa dahil edilir.
        Son olarak, collect(Collectors.toList()) ile filtrelenen oyuncular bir List<Player>'e dönüştürülür
         */
    }

    public List<Player> getPlayerByName(String searchName){

        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchName.toLowerCase())).collect(Collectors.toList());
    }

    public List<Player> getPlayerByPos(String searchPos){

        return playerRepository.findAll().stream()
                .filter(player -> player.getPos().toLowerCase().contains(searchPos.toLowerCase())).collect(Collectors.toList());
    }


    public List<Player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition(String team, String position){
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player){
        playerRepository.save(player);
        return player;
    }


    public  Player updatePlayer(Player updatedPlayer){
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
        //Optional kullanarak, nesne boş (null) olsa bile güvenli bir şekilde kontrol yapabilirsiniz.

        if(existingPlayer.isPresent()){
            Player playerToUpdate = existingPlayer.get();

            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }

        return null;
    }




    /*

    @Transactional Anotasyonu
        @Transactional, bir işlem (transaction) kapsamı belirler. Bu şu anlama gelir:
        Metot çalışmaya başladığında bir veri tabanı işlemi başlar.
        Eğer metot başarılı bir şekilde tamamlanırsa, işlem (transaction) commit edilir (değişiklikler kaydedilir).
        Eğer metot sırasında bir hata veya istisna oluşursa, işlem rollback edilir (değişiklikler geri alınır).
        Bu, veri tutarlılığını sağlamak için kritik bir özelliktir
     */

    @Transactional
    public void deletePlayer(String playerName){
        playerRepository.deleteByName(playerName);
    }


}
