package com.sulbin.chatweb.account;

import com.sulbin.chatweb.account.dto.AccountDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원"})
@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 시키는 API")
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
        return ResponseEntity.ok().body("Home test");
    }

    @GetMapping("/hometest")
    public ResponseEntity<?> hometest(){
        return ResponseEntity.ok().body("Home test");
    }
}
