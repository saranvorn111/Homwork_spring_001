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

    @Select("SELECT EXISTS(SELECT * FROM account_types WHERE id = #{id})")
    boolean existById(@Param("id") Integer id);

    @UpdateProvider(type=AccountTypeProvider.class,method = "buildUpdateByIdSql")
    void updateAccountTypeById(@Param("u") AccountType accountType);

    @DeleteProvider(type= AccountTypeProvider.class,method = "buildDeleteByIdSql")
    void deleteById(@Param("id") Integer id);

    @UpdateProvider(type = AccountTypeProvider.class,method = "buildUpdateIsDeletedByIdSql")
    void updateIsDeletedById(@Param("id") Integer id,@Param("status") boolean status);

}

