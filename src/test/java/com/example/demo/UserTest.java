package com.example.demo;


import com.example.demo.models.dto.AuthProvider;
import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.UserStatus;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.example.demo.models.entity.UserPermission;
import com.example.demo.models.in.UserIn;
import com.example.demo.repositories.PermissionRepo;
import com.example.demo.repositories.UserPerRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.impl.AuthServiceImpl;
import com.example.demo.services.impl.AdminServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(WebSecurityConfig.class)
//@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "com.sun.org.apache.xalan.*"})
public class UserTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private UserPerRepo userPerRepo;
    @Mock
    private PermissionRepo permissionRepo;

    @InjectMocks
    private AdminServiceImpl userService;
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void getRoleAdminSuccess(){
        Permission permission = Permission.builder().name("ADMIN").id(1L).build();
        when(permissionRepo.findByName("ADMIN")).thenReturn(Optional.of(permission));
        User user = User.builder().username("phuong99").id(1L).password("abcxyz").status(UserStatus.ACTIVE.name()).provider(AuthProvider.local).build();
        when(userRepo.save(any())).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("abcxyz");
        UserPermission userPermission = UserPermission.builder().userId(user.getId()).perId(permission.getId()).build();
        when(userPerRepo.save(userPermission)).thenReturn(userPermission);
        UserIn userIn = UserIn.builder().username("phuong99").password("123456").role("ADMIN").build();
        ResponseEntity<MessageDTO<Object>> user1 = userService.create(userIn);
        assertNotNull(user1);
    }

//    @Test
//   public void testLogin(){
//        ResponseEntity<MessageDTO<Object>> res = null;
//        LoginDTO loginDTO = LoginDTO.builder().username("ABC").password("123456").build();
//
//   }
//    @Test
//    public void whenAddCalledVerified() {
//
//        User myList = mock(User.class);
//        doNothing().when(myList).setImage(isA(String.class));
//        myList.setImage("0");
//
//        verify(myList, times(1)).setImage( "0");
//    }


}
