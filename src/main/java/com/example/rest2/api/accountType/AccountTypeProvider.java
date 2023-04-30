package com.example.rest2.api.accountType;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private final String tableNameAccount ="account_types";
    public String buildSelectSql(){
        return new SQL(){{
            //TODO:
            SELECT("*");
            FROM(tableNameAccount);
        }}.toString();
    }

    public String buildInsertAccountSql(){
        return new SQL(){{
            INSERT_INTO(tableNameAccount);
            VALUES("name","#{a.name}");
        }}.toString();
    }
    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableNameAccount);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildUpdateByIdSql(){
        return new SQL(){{
            UPDATE(tableNameAccount);
            SET("name = #{u.name}");
            WHERE("id=#{u.id}");
        }}.toString();
    }
    public String buildDeleteByIdSql(){
        return new SQL(){{
            DELETE_FROM(tableNameAccount);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildUpdateIsDeletedByIdSql(){
        return new SQL(){{
            UPDATE(tableNameAccount);
            SET("name = #{status}");
            WHERE("id = #{id}");

        }}.toString();
    }
}
