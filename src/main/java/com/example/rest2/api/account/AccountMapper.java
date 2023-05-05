package com.example.rest2.api.account;

import com.example.rest2.api.account.account_web.AccountDto;
import com.example.rest2.api.accountType.AccountType;
import com.example.rest2.api.accountType.AccountTypeProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AccountMapper {
    @SelectProvider(type = AccountProvider.class,method = "buildSelectAccountSql")
    List<Account> selectAccount();

    @InsertProvider(type = AccountProvider.class,method = "buildInsertAccountSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("a") Account account);

    @SelectProvider(type= AccountProvider.class,method="buildSelectByIdSql")
    Optional<Account> selectById(@Param("id") Integer id);
    @DeleteProvider(type = AccountProvider.class,method = "buildDeleteByIdSql")
    void deleteAccountById(@Param("id") Integer id);

    @Select("SELECT EXISTS(SELECT * FROM accounts WHERE id = #{id})")
    boolean existAccountById(@Param("id") Integer id);
}
