package com.example.financial_favorite.Repository;

import com.example.financial_favorite.entity.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, Long> {

}
