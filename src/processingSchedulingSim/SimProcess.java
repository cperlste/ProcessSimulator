package processingSchedulingSim;

import java.util.Random;

public class SimProcess {
	private int pID;
	private String procName;
	private int totalInstructions;
	private Random randomGen= new Random();
	public SimProcess(int pID, String procName, int totalInstructions) {
		this.pID=pID;
		this.procName=procName;
		this.totalInstructions=totalInstructions;
	}
	public ProcessState execute(int i) {
		System.out.println("PID: "+pID+" Process Name: "+procName+" Instruction Number: "+ i);
		if(i>=totalInstructions) {
			return ProcessState.FINISHED;
		}
		else if (randomGen.nextDouble()<.15){
			return ProcessState.BLOCKED;
		}
		else {
			return ProcessState.READY;
		}
	}
	public int getPID() {
		return this.pID;
	}
	@Override
	public String toString() {
		return procName;
	}
	public int getTotalInstructions() {
		return totalInstructions;
	}
}
