package repositories.impl;

import jpa.JpaManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import repositories.UserRepository;

@AllArgsConstructor
@RequiredArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    JpaManager jpaManager;

}
