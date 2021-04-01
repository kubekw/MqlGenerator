package com.example.application;

import com.example.application.PersonsNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NamesRepo extends JpaRepository<PersonsNames, Integer> {
}
