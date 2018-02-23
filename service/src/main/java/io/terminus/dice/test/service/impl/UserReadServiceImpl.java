package io.terminus.dice.test.service.impl;

import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.dice.test.User;
import io.terminus.dice.test.service.UserReadService;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class UserReadServiceImpl implements UserReadService{
    @Override
    public User findById(Long id) {
        return null;
    }
}
