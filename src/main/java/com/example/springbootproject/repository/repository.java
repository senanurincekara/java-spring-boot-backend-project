package com.example.springbootproject.repository;


import com.example.springbootproject.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
extends JpaRepository<Player, String>:
    JpaRepository: Spring Data JPA tarafından sağlanan bir arayüzdür ve veri tabanı işlemlerini kolaylaştırır.
    CRUD (Create, Read, Update, Delete) işlemleri, sayfalama ve sıralama gibi yaygın işlemleri otomatik olarak sağlar.
    Yani, temel veri tabanı sorgularını sizin yazmanıza gerek kalmaz.

<Player, String>:
    İlk parametre (Player), bu repository'nin işleyeceği varlık sınıfını belirtir. Yani, bu repository Player varlıklarıyla çalışacak.


**/

@Repository
public interface repository extends JpaRepository<Player , String> {

    void deleteByName(String playerName);
    Optional<Player> findByName(String name);

    /*

    Neden Optional Kullanılır?
        NullPointerException'dan Kaçınmak:

        Veri tabanında bir sonuç bulunamadığında, findByName gibi bir metot null dönebilir. Eğer bu null değeri kontrol etmeden kullanmaya çalışırsanız, NullPointerException hatası alabilirsiniz.
        Optional, bu durumdan kaçınmak için tasarlanmıştır. Null yerine bir "boş kapsayıcı" (empty container) döner.
        Daha Anlaşılır Kod Yazımı:

        Optional ile, bir değerin var olup olmadığını açıkça belirtirsiniz. Bu da kodun daha okunabilir ve bakımı kolay olmasını sağlar.
        Fonksiyonel Programlama Desteği:

        Optional, lambda ifadeleri ve fonksiyonel programlama ile güzel bir şekilde entegre olur.
        Örneğin, bir değer bulunduğunda ne yapılması gerektiğini kolayca tanımlayabilirsiniz.


    **/
}
