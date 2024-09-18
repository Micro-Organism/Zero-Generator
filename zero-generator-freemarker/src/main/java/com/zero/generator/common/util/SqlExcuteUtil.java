package com.zero.generator.common.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlExcuteUtil {

    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 替换文件内容
     *
     * @param oldString 旧字符串
     * @param newString 新字符串
     * @param path      文件路径
     */
    public static void alterStringToROM(String oldString, String newString, String path) {
        File file = new File(path); //创建目标文件
        try {
            long start = System.currentTimeMillis();
            BufferedReader br_File = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            CharArrayWriter caw = new CharArrayWriter();
            String string;
            int sum = 0;
            while ((string = br_File.readLine()) != null) {
                //判断是否包含目标字符，包含则替换
                if (string.contains(oldString)) {
                    string = new String(string.replace(oldString, newString));
                    sum++;
                }
                //写入内容并添加换行
                caw.write(string);
                caw.write("\r\n");
            }
            br_File.close();
            BufferedWriter bw_File = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            caw.writeTo(bw_File);
            caw.close();
            bw_File.close();
            long time = System.currentTimeMillis() - start;
            System.out.println(sum + "个" + oldString + "替换成" + newString + "耗费时间:" + time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
     * @param str   字符串
     * @return   字符串
     */
    public static String underlineToHump (String str){
        //正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配的子串替换成指定字符串，并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的字符串也添加到StringBuffer对象中
            matcher.appendTail(sb);
        } else {
            //去除除字母之外的前面带的下划线
            return sb.toString().replaceAll("_", "");
        }
        return underlineToHump(sb.toString());
    }

    /**
     * 插入菜单
     * @param driver    驱动
     * @param url   url
     * @param user   用户名
     * @param password   密码
     * @param entityName    实体类名
     * @return   boolean
     */
    public boolean putMenu(String driver,String url,String user,String password,String entityName){
//         String entityName = "";
//         // 驱动程序名
//         String driver = "com.mysql.cj.jdbc.Driver";
//         // URL指向要访问的数据库名scutcs
//         String url = "jdbc:mysql://localhost:3306/zero?serverTimezone=GMT%2B8&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false";
//         // MySQL配置时的用户名
//         String user = "root";
//         // MySQL配置时的密码
//         String password = "root@123";
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            Connection conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed()) {
                System.out.println("mysql链接成功");
            }
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            String baseId = UUID.randomUUID().toString();
            String querySql = "select base_id from sys_menu where path = 'template' and parent_id = 0";
            ResultSet queryBaseId = statement.executeQuery(querySql);

            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");
            System.out.println("-----------------");
            System.out.println(" 学号" + "\t" + " 姓名");
            System.out.println("-----------------");
            String menuId = null;
            while (queryBaseId.next()) {
                // 选择sname这列数据
                menuId = queryBaseId.getString("base_id");
                // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                // 然后使用GB2312字符集解码指定的字节数组
                menuId = new String(menuId.getBytes("ISO-8859-1"), "GB2312");
                // 输出结果
                if (!menuId.equals("") && menuId != null) {
                    String insertSql = "INSERT INTO `sys_menu`(`base_id`, `base_name`, `base_create_time`, " +
                            "`base_update_time`, `parent_id`, `path`, `component`, `target`, `menu_type`, " +
                            "`visible`, `perms`, `icon`, `creator`, `update_by`, `base_note`, `system_id`, " +
                            "`tenant_id`, `is_delete`, `sort`, `is_frame`, `path_method`)" +
                            " VALUES (uuid(), '列表模板', now(), '2023-04-19 13:46:22', '" + menuId + "', 'userSso', 'template/" + entityName + "/index', 'menuItem', 'C', '0', NULL, 'build', '3d611d48853f11ea9e870221860e9b7e', '', NULL, '0', '', b'0', 0, '1', NULL);\n";
                } else {
                    // 要执行的SQL语句
                    String sql = "INSERT INTO `sys_menu`(`base_id`, `base_name`, `base_create_time`, " +
                            "`base_update_time`, `parent_id`, `path`, `component`," +
                            " `target`, `menu_type`, `visible`, `perms`, `icon`, " +
                            "`creator`, `update_by`, `base_note`, `system_id`, " +
                            "`tenant_id`, `is_delete`, `sort`, `is_frame`, `path_method`)" +
                            " VALUES ('" + baseId + "', '梦想管理1111',NOW(), null, '0', 'template', NULL, " +
                            "'menuItem', 'M', '0', NULL, 'example', " +
                            "'3d611d48853f11ea9e870221860e9b7e', '', NULL, '0', '', b'0', 10, '1', NULL);";
                    // 结果集
                    boolean rs = statement.execute(sql);
                    if (rs) {
                        String insertSql = "INSERT INTO `sys_menu`(`base_id`, `base_name`, `base_create_time`, " +
                                "`base_update_time`, `parent_id`, `path`, `component`, `target`, `menu_type`, " +
                                "`visible`, `perms`, `icon`, `creator`, `update_by`, `base_note`, `system_id`, " +
                                "`tenant_id`, `is_delete`, `sort`, `is_frame`, `path_method`)" +
                                " VALUES (uuid(), '列表模板', now(), '2023-04-19 13:46:22', '" + baseId + "', " +
                                "'userSso', 'template/" + entityName + "/index', 'menuItem', 'C', '0', NULL, " +
                                "'build', '3d611d48853f11ea9e870221860e9b7e', '', NULL, '0', '', b'0', 0, '1', NULL);\n";
                        return statement.execute(insertSql);
                    }
                }
            }
            queryBaseId.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
