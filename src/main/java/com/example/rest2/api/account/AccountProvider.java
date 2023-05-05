package com.example.rest2.api.account;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {
    public final String tableAccount = "accounts";
   public String buildSelectAccountSql(){
       return new SQL(){{
           SELECT("*");
           FROM(tableAccount);
       }}.toString();
   }
   public String buildInsertAccountSql(){
       return new SQL(){{
           INSERT_INTO(tableAccount);
           VALUES("account_no","#{a.accountNo}");
           VALUES("account_name","#{a.accountName}");
           VALUES("profile","#{a.profile}");
           VALUES("pin","#{a.pin}");
           VALUES("password","#{a.password}");
           VALUES("phone_number","#{a.phoneNumber}");
           VALUES("transfer_limit","#{a.transferLimit}");
           VALUES("account_type","#{a.accountType}");

       }}.toString();
   }

   public String buildSelectByIdSql(){
       return new SQL(){{
           SELECT("*");
           FROM(tableAccount);
           WHERE("id = #{id}");
       }}.toString();
   }

   public String buildDeleteByIdSql(){
       return new SQL(){{
           DELETE_FROM(tableAccount);
           WHERE("id = #{id}");
       }}.toString();
   }
}
