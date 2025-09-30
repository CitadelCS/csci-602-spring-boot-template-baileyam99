package edu.citadel.api;

import edu.citadel.api.request.AccountRequestBody;
import edu.citadel.api.request.AllAccountRequestBody;
import edu.citadel.api.response.AllAccountResponse;
import edu.citadel.dal.AccountRepository;
import edu.citadel.dal.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountEndpoints {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountEndpoints(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Account> createAccount(@RequestBody AccountRequestBody accountRequestBody) {

    Account account = new Account();
    account.setEmail(accountRequestBody.getEmail());
    account.setPassword(accountRequestBody.getPassword());
    account.setUsername(accountRequestBody.getUsername());

    Account rtnAccount = accountRepository.save(account);

    return new ResponseEntity<>(rtnAccount, HttpStatus.CREATED);
  }

  @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Account> getByUsername(@PathVariable String username) {
    Account rtnAccount = accountRepository.findAccountByUsername(username);
    if (rtnAccount != null) {
      return new ResponseEntity<>(rtnAccount, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Account> getById(@PathVariable Long id) {
    Account rtnAccount = accountRepository.findById(id).orElse(null);
    if (rtnAccount != null) {
      return new ResponseEntity<>(rtnAccount, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getAllUsers(@RequestBody AllAccountRequestBody requestBody) {
      try {
          AllAccountResponse rtnResponse = new AllAccountResponse();
          rtnResponse.setCurrentPage(requestBody.getCurrentPage());
          rtnResponse.setItemsPerPage(requestBody.getItemsPerPage());

          List<Account> rtnAccounts;
          if (requestBody.getItemsPerPage() == -1) {
              rtnAccounts = accountRepository.findAll();
          } else {
              PageRequest pageRequest = PageRequest.of(requestBody.getCurrentPage() - 1, requestBody.getItemsPerPage());
              Page<Account> entities = accountRepository.findAll(pageRequest);
              rtnAccounts = entities.getContent();
              rtnResponse.setTotalItems(accountRepository.findAll().size());
          }
          rtnResponse.setAccounts(rtnAccounts);
          return new ResponseEntity<>(rtnResponse, HttpStatus.OK);

      } catch (Exception e) {
          String message = e.getMessage();
          if (Objects.equals(message, "Page size must not be less than one")) {
              String errorMsg = message.replaceFirst("one", "negative one and must not be zero");
              log.error(errorMsg, e);
              return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
          } else {
              log.error(message, e);
              return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }
  }
}
