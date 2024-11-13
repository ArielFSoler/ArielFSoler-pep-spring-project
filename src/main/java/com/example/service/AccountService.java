package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //1.Create new account
    public Account register(Account account) throws Exception{
        if(accountRepository.findByUsername(account.getUsername()) != null)
            throw new Exception("Username already exists");

        return accountRepository.save(account);
    }

    //2.Process User Login
    public Account login(Account account)throws Exception{
        Account foundAccount = accountRepository.findByUsername(account.getUsername());

        if(foundAccount == null || !foundAccount.getPassword().equals(account.getPassword())){
            throw new Exception("Invalid username or password");
        }

        return foundAccount;
    }

}
