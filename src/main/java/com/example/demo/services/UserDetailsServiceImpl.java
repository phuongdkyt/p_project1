package com.example.demo.services;


import com.example.demo.models.dto.UserStatus;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.example.demo.models.entity.UserPermission;
import com.example.demo.repositories.PermissionRepo;
import com.example.demo.repositories.UserPerRepo;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private UserPerRepo userPerRepo;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE.name());
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<UserPermission> userPermission = userPerRepo.findAllByUserId(user.getId());
        Set<Long> perId = userPermission.stream().map(UserPermission::getPerId).collect(Collectors.toSet());
        List<Permission> permissions = permissionRepo.findAllByIdIn(perId);

        return UserDetailsImpl.build(user,permissions);
    }

}
