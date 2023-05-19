package com.example.laowuguanli.biz;

public class SqlLang {

    //本地广播表名
    public static final String CREATE_TABLE = "information";

    //创建数据库表
    public static String create_table() {
        return "create table " + CREATE_TABLE + "(id int primary key,name varchat(20),company varchat(20)," +
                "city varchat(20),sex varchar(20),identitycard varchat(20),companyfront varchat(20)," +
                "companyrear varchat(20)," + "operatingpost varchat(20),bankid varchat(20))";
    }


}
