package com.bank.solutions.dto;

import com.bank.solutions.dto.AccountDTO;
import com.bank.solutions.model.Account;
import com.bank.solutions.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    AccountDTO accountToAccountDTO(Account account);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserIdToUser")
    Account accountDTOToAccount(AccountDTO accountDTO);

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}
