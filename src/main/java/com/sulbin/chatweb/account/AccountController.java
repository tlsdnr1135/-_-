package com.sulbin.chatweb.account;

import com.sulbin.chatweb.account.dto.AccountDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Api(tags = {"회원"})
@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    private final AccountService accountService;
    private final Environment environment;

    public AccountController(AccountService accountService, Environment environment) {
        this.accountService = accountService;
        this.environment = environment;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 시키는 API ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "아이디"),
            @ApiImplicitParam(name = "password", value = "비번")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),//response = AccountDto.class
            @ApiResponse(code = 204, message = "member not exists")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<AccountDto> signUp(@RequestBody AccountDto accountDto){
        accountService.signUp(accountDto);
        return ResponseEntity.ok().body(accountDto);
    }

    //테스트 홈
    @GetMapping("/home")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok().body("Home test-good");
    }

    @GetMapping("/hometest")
    public ResponseEntity<?> hometest(){
        return ResponseEntity.ok().body("Home test");
    }

    //nginx시 -> 테스트 코드는 나중에 작성
    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }//결과값 예상 real2

}
