package com.example.rest2.api.accountType;

import com.example.rest2.api.user.User;
import com.example.rest2.api.user.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AccountTypeMapper {
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectSql")
    List<AccountType> select();

    @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertAccountSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("a") AccountType accountType);

    @SelectProvider(type= AccountTypeProvider.class,method="buildSelectByIdSql")
    Optional<AccountType> selectById(@Param("id") Integer id);
}
