package io.terminus.dice.test.service;

import io.terminus.dice.test.User;

public interface UserReadService {
    User findById(Long id);
}
