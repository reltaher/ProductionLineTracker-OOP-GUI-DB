package productlinetracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionItemCounter {

  private int audioItemCounter;
  private int audioMobileItemCounter;
  private int visualItemCounter;
  private int visualMobileItemCounter;
  private int productionRecordCounter;

  public ProductionItemCounter() {
    this.audioItemCounter = 0;
    this.audioMobileItemCounter = 0;
    this.visualItemCounter = 0;
    this.visualMobileItemCounter = 0;
    this.productionRecordCounter = 0;
  }

  public ProductionItemCounter(int audioItemCounter, int audioMobileItemCounter,
      int visualItemCounter, int visualMobileItemCounter, int productionRecordCounter) {
    this.audioItemCounter = audioItemCounter;
    this.audioMobileItemCounter = audioMobileItemCounter;
    this.visualItemCounter = visualItemCounter;
    this.visualMobileItemCounter = visualMobileItemCounter;
    this.productionRecordCounter = productionRecordCounter;
  }

  public void countItemsFromDatabase() {
    String itemSerialNumber = "SELECT SERIAL_NUM FROM PRODUCTIONRECORD";
    try {
      Connection connection = Database.getInstance().getConnection();
      PreparedStatement ps = connection.prepareStatement(itemSerialNumber);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String serialNum = rs.getString("serial_num");
        // auto-increment Audio counter if SERIAL_NUM table contains "AU"
        if (serialNum.contains("AU")) {
          audioItemCounter++;
        }
        // auto-increment AudioMobile counter if SERIAL_NUM table contains "AM"
        if (serialNum.contains("AM")) {
          audioMobileItemCounter++;
        }
        // auto-increment Visual counter if SERIAL_NUM table contains "VI"
        if (serialNum.contains("VI")) {
          visualItemCounter++;
        }
        // auto-increment VisualMobile counter if SERIAL_NUM table contains "VM"
        if (serialNum.contains("VM")) {
          visualMobileItemCounter++;
        }
      }
      rs.close();
      ps.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public int getAudioItemCounter() {
    return audioItemCounter;
  }

  public void setAudioItemCounter(int audioItemCounter) {
    this.audioItemCounter = audioItemCounter;
  }

  public int getAudioMobileItemCounter() {
    return audioMobileItemCounter;
  }

  public void setAudioMobileItemCounter(int audioMobileItemCounter) {
    this.audioMobileItemCounter = audioMobileItemCounter;
  }

  public int getVisualItemCounter() {
    return visualItemCounter;
  }

  public void setVisualItemCounter(int visualItemCounter) {
    this.visualItemCounter = visualItemCounter;
  }

  public int getVisualMobileItemCounter() {
    return visualMobileItemCounter;
  }

  public void setVisualMobileItemCounter(int visualMobileItemCounter) {
    this.visualMobileItemCounter = visualMobileItemCounter;
  }

  public int getProductionRecordCounter() {
    return productionRecordCounter;
  }

  public void setProductionRecordCounter(int productionRecordCounter) {
    this.productionRecordCounter = productionRecordCounter;
  }
}
