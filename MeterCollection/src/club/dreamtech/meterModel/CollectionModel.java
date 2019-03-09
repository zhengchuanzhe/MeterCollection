package club.dreamtech.meterModel;

/**
 * 采集实体类
 * @author cz
 *
 */
public class CollectionModel {
   private String meterAddressCode;
   private String indentificationCode;
   private String value;
   /**
    * 获取电表地址码
    * @return
    */
   public String getMeterAddressCode() {
	   return meterAddressCode;
   }
   /**
    * 设置电表地址码
    * @param meterAddressCode
    */
   public void setMeterAddressCode(String meterAddressCode) {
	   this.meterAddressCode = meterAddressCode;
   }
   /**
    * 获取标识码
    * @return
    */
   public String getIndentificationCode() {
	   return indentificationCode;
   }
   /**
    * 设置标识码
    * @param indentificationCode
    */
   public void setIndentificationCode(String indentificationCode) {
	   this.indentificationCode = indentificationCode;
   }
   /**
    * 获取采集值
    * @return
    */
   public double getValue() {
	   return Double.parseDouble(value);
   }
   /**
    * 设置采集值
    * @param value
    */
   public void setValue(String value) {
	   this.value = value;
   }
   
}
