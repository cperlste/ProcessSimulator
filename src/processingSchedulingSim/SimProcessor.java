package processingSchedulingSim;

import java.util.Random;

public class SimProcessor {
	private SimProcess currentProc;
	private Random randomGen= new Random();
	private int register1;
	private int register2;
	private int register3;
	private int register4;
	private int currInstruction;
	public SimProcessor() {
		setRegisters();
	}
	private void setRegisters() {
		register1=randomGen.nextInt();
		register2=randomGen.nextInt();
		register3=randomGen.nextInt();
		register4=randomGen.nextInt();
	}
	public int getRegisterValue() {
		return randomGen.nextInt();
	}
	public void setRegister1(int register1) {
		this.register1 = register1;
	}
	public void setRegister2(int register2) {
		this.register2 = register2;
	}
	public void setRegister3(int register3) {
		this.register3 = register3;
	}
	public void setRegister4(int register4) {
		this.register4 = register4;
	}
	public int getCurrInstruction() {
		return currInstruction;
	}
	public int getRegister1() {
		return register1;
	}
	
	public int getRegister2() {
		return register2;
	}

	public int getRegister3() {
		return register3;
	}

	public int getRegister4() {
		return register4;
	}
	
	public void setCurrInstruction(int currInstruction) {
		this.currInstruction=currInstruction;
	}
	public ProcessState executeNextInstruction() {
		setRegisters();
		ProcessState result=currentProc.execute(currInstruction);
		currInstruction++;  
		return result;
	}
	public void setCurrentProcess(SimProcess process) {
		this.currentProc=process;
	}
	public SimProcess getCurrentProcess() {
		return this.currentProc;
	}
	
}
