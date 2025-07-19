package com.example.photoapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query(value = "SELECT * FROM photo ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Photo findRandom();
}
