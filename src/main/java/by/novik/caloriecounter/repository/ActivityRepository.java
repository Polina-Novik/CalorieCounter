package by.novik.securitybank.repository;

import by.novik.securitybank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    Card findByCardNumber(Long cardNumber);


}
