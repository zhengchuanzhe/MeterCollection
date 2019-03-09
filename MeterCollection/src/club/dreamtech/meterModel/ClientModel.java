package club.dreamtech.meterModel;

import club.dreamtech.meterHandler.DTLMessageHandler;
import club.dreamtech.meterHandler.MeterCollectionHandler;

public class ClientModel {
	
	public ClientModel() {
		
	}
	
	public ClientModel(DTLMessageHandler myDTLMessageHandler,byte[] meterCode) {
		this.myDTLMessageHandler=myDTLMessageHandler;
		this.meterCode=meterCode;
	}
	
	private DTLMessageHandler myDTLMessageHandler;

	public DTLMessageHandler getMyDTLMessageHandler() {
		return myDTLMessageHandler;
	}

	public void setMyDTLMessageHandler(DTLMessageHandler myDTLMessageHandler) {
		this.myDTLMessageHandler = myDTLMessageHandler;
	}

	private byte[] meterCode;

	public byte[] getMeterCode() {
		return meterCode;
	}

	public void setMeterCode(byte[] meterCode) {
		this.meterCode = meterCode;
	}
}
