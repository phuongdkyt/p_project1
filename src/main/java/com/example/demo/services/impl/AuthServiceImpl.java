package com.example.demo.services.impl;


import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.Response;
import com.example.demo.models.dto.UserLogin;
import com.example.demo.models.dto.UserStatus;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.example.demo.models.entity.UserPermission;
import com.example.demo.models.in.LoginDTO;
import com.example.demo.repositories.PermissionRepo;
import com.example.demo.repositories.UserPerRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AuthService;
import com.example.demo.services.JwtUser;
import com.example.demo.services.UserJwt;
import com.example.demo.utils.UserUtils;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUser jwtUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private UserPerRepo userPerRepo;

    public ResponseEntity<MessageDTO<Object>> login(HttpServletRequest request, LoginDTO loginDTO) {
        User user = userRepo.findByUsernameAndStatus(loginDTO.getUsername(), UserStatus.ACTIVE.name());

        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Response.unauthorized("Thông tin tài khoản hoặc mật khẩu không chính xác!");
        }
        List<GrantedAuthority> authorities = getAuthorities(user.getId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = UserUtils.toRole(authorities);
        String jwt = jwtUser.generateJWT(UserJwt.from(user, role));
        user.setLastLoginDate(ZonedDateTime.now());
        userRepo.save(user);

        return Response.ok(
                UserLogin.builder().
                        username(user.getUsername()).
                        roles(UserUtils.toRoleResponse(role)).
                        id(user.getId()).accessToken(jwt).build());
    }

    private List<GrantedAuthority> getAuthorities(Long userId) {
        List<UserPermission> userPermission = userPerRepo.findAllByUserId(userId);
        Set<Long> perId = userPermission.stream().map(UserPermission::getPerId).collect(Collectors.toSet());
        List<Permission> permissions = permissionRepo.findAllByIdIn(perId);
        return permissions.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String testSave(){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 800000; i++) {
            String userName = Generators.randomBasedGenerator().generate().toString();
            User user = User.builder().username(userName).build();
            list.add(user);
        }
        saveBatch(list);
        return "Save OK em";
    }
    public void saveBatch(List<User> userMax) {
        for (int j = 0; j < userMax.size(); j += 1000) {
            final List<User> batchList = userMax.subList(j,
                    Math.min(j + 1000, userMax.size()));

            String sql = "INSERT INTO users(username,password,created_date,provider) values (?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User serial = batchList.get(i);
                    ps.setString(1,serial.getUsername());
                    ps.setString(2,"123456");
                    ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(4, "provider");
                }

                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
            });
        }
    }
}
