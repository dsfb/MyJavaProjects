package ru.tigertv.calculator.operation;

import java.math.BigInteger;

import javax.swing.JOptionPane;

import ru.tigertv.calculator.model.CalcModel;

public class DivisionOperation implements ICalculatorOperation {

	@Override
	public void calculate(CalcModel model) {
		BigInteger result = new BigInteger(model.getFirstOperand());
		BigInteger second = new BigInteger(model.getSecondOperand());
		
		if (second.equals(BigInteger.ZERO)) {
			JOptionPane.showMessageDialog(null, "Invalid Operation!");
			model.reset();
			return;
		}
		
		result = result.divide(second);
		
		model.setResult(result.toString());
	}
	@Override
	public String symbol() {
		return "/";
	}
	
	@Override
	public boolean isUnaryOperation() {
		// TODO Auto-generated method stub
		return false;
	}

}