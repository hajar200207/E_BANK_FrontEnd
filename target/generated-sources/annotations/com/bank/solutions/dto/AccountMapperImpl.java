package com.bank.solutions.dto;

import com.bank.solutions.model.Account;
import com.bank.solutions.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-18T18:09:48+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO accountToAccountDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setUserId( accountUserUserId( account ) );
        accountDTO.setAccountId( account.getAccountId() );
        accountDTO.setType( account.getType() );
        accountDTO.setBalance( account.getBalance() );
        accountDTO.setCreationDate( account.getCreationDate() );

        return accountDTO;
    }

    @Override
    public Account accountDTOToAccount(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account account = new Account();

        account.setUser( mapUserIdToUser( accountDTO.getUserId() ) );
        account.setAccountId( accountDTO.getAccountId() );
        account.setType( accountDTO.getType() );
        account.setBalance( accountDTO.getBalance() );
        account.setCreationDate( accountDTO.getCreationDate() );

        return account;
    }

    private Long accountUserUserId(Account account) {
        if ( account == null ) {
            return null;
        }
        User user = account.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
