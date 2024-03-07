package com.mmt.mmtApp.repository;

import com.mmt.mmtApp.models.City;
import com.mmt.mmtApp.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}