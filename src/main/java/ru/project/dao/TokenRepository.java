package ru.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.entity.OAuthAccessToken;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<OAuthAccessToken, Long> {

    Optional<OAuthAccessToken> findByUsername(String username);

    Optional<OAuthAccessToken> findByAccessToken(String accessToken);
}
