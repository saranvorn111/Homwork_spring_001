package com.example.rest2.api.accountType;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private final String tableNameAccount ="account_types";
    public String buildSelectSql(){
        return new SQL(){{
            //TODO:
            SELECT("*");
            FROM(tableNameAccount);
//            WHERE("id= #{id}");
        }}.toString();
    }

//    public String buildInsertAccountSql(){
//        return new SQL(){{
//            INSERT_INTO(tableNameAccount);
//            VALUES("name","#{a.name}");
//        }}.toString();
//    }
}
