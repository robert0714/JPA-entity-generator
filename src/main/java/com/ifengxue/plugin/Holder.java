package com.ifengxue.plugin;
 
import com.ifengxue.plugin.util.JdbcConfigUtil; 
import fastjdbc.FastJdbc;
import java.io.IOException; 

public class Holder {
 
  private static volatile FastJdbc fastJdbc;
  private static volatile JdbcConfigUtil jdbcConfigUtil;
  

  public static synchronized void registerFastJdbc(FastJdbc fastJdbc) {
    if (Holder.fastJdbc != null) {
      try {
        Holder.fastJdbc.close();
      } catch (IOException e) {
        // ignore
      }
    }
    Holder.fastJdbc = fastJdbc;
  }

  public static synchronized FastJdbc getFastJdbc() {
    return fastJdbc;
  }

  public static synchronized void registerJdbcConfigUtil(JdbcConfigUtil jdbcConfigUtil) {
    Holder.jdbcConfigUtil = jdbcConfigUtil;
  }

  public static synchronized JdbcConfigUtil getJdbcConfigUtil() {
    return Holder.jdbcConfigUtil;
  }
}
