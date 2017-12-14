package com.yolotengo.gatewayApp.dto;

import java.util.List;

public class RowDna {

	private int RowNumber;
	private List<String> DnaLine;
	
	public int getRowNumber() {
		return RowNumber;
	}
	public void setRowNumber(int rowNumber) {
		RowNumber = rowNumber;
	}
	public List<String> getDnaLine() {
		return DnaLine;
	}
	public void setDnaLine(List<String> dnaLine) {
		DnaLine = dnaLine;
	}
}
