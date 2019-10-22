package processingSchedulingSim;

import java.util.ArrayList;
import java.util.Random;

public class Main {
public static void main(String[]args) {
	final int QUANTUM=5;
	Random randomGen= new Random();
	SimProcessor processor=new SimProcessor();
	SimProcess process1= new SimProcess(1, "Word", 100);
	ProcessControlBlock pcb1= new ProcessControlBlock(process1);
	SimProcess process2= new SimProcess(2, "Excel", 150);
	ProcessControlBlock pcb2= new ProcessControlBlock(process2);
	SimProcess process3= new SimProcess(3, "Powerpoint", 125);
	ProcessControlBlock pcb3= new ProcessControlBlock(process3);
	SimProcess process4= new SimProcess(4, "Google", 200);
	ProcessControlBlock pcb4= new ProcessControlBlock(process4);
	SimProcess process5= new SimProcess(5, "Spotify", 387);
	ProcessControlBlock pcb5= new ProcessControlBlock(process5);
	SimProcess process6= new SimProcess(6, "Weather", 400);
	ProcessControlBlock pcb6= new ProcessControlBlock(process6);
	SimProcess process7= new SimProcess(7, "Eclipse", 290);
	ProcessControlBlock pcb7= new ProcessControlBlock(process7);
	SimProcess process8= new SimProcess(8, "Publisher", 256);
	ProcessControlBlock pcb8= new ProcessControlBlock(process8);
	SimProcess process9= new SimProcess(9, "LinkedIn", 175);
	ProcessControlBlock pcb9= new ProcessControlBlock(process9);
	SimProcess process10=new SimProcess(10, "Putty", 324);
	ProcessControlBlock pcb10= new ProcessControlBlock(process10);
	
	ArrayList<ProcessControlBlock>readyList= new ArrayList<ProcessControlBlock>();
	readyList.add(pcb1);
	readyList.add(pcb2);
	readyList.add(pcb3);
	readyList.add(pcb4);
	readyList.add(pcb5);
	readyList.add(pcb6);
	readyList.add(pcb7);
	readyList.add(pcb8);
	readyList.add(pcb9);
	readyList.add(pcb10);
	ArrayList<ProcessControlBlock>blockedList= new ArrayList<ProcessControlBlock>();
	int instructionIterator=0;
	int procIndex=0;
	for(int x=0; x<3000;x++) {
		System.out.println("Step "+(x+1));
		ProcessControlBlock currentPCB= readyList.get(procIndex);
		readyList.remove(currentPCB);
		processor.setCurrentProcess(currentPCB.getCurrentProc());
		processor.setCurrInstruction(instructionIterator);
		ProcessorState state= currentPCB.getCurrentProc().execute(instructionIterator);
				if(state==ProcessorState.FINISHED) {
					contextSwitch(processor, blockedList, readyList, currentPCB);
				}
				else if(state==ProcessorState.BLOCKED) {
						blockedList.add(currentPCB);
						System.out.println("***Process Blocked***");
						contextSwitch(processor, blockedList, readyList, currentPCB);
				}
				else if(instructionIterator!=0 && instructionIterator%5==0) {
					System.out.println("***Quantum Expired***");
					readyList.add(currentPCB);
					contextSwitch(processor, blockedList, readyList, currentPCB);
				}
				else if(state==ProcessorState.READY){
					instructionIterator++;
				}
				else if(instructionIterator>=currentPCB.getCurrentProc().getTotalInstructions())
					procIndex++;
					instructionIterator=0;
			for(int y=0; y<blockedList.size(); y++) {
				if(randomGen.nextDouble()<.3) {
					readyList.add(blockedList.get(y));
				}
			}
			if (readyList.size()==0) {
				contextSwitch(processor, blockedList, readyList, currentPCB);
			}
		}
	}

private static void contextSwitch(SimProcessor processor, ArrayList<ProcessControlBlock> blockedList,
	ArrayList<ProcessControlBlock> readyList, ProcessControlBlock currentPCB) {
	contextSwitchSave(currentPCB, processor, blockedList);
	ProcessControlBlock toRestore=readyList.get(0);
	contextSwitchRestore(toRestore);
}

private static void contextSwitchRestore(ProcessControlBlock toRestore) {
	System.out.println("***Context Switch: restoring process "+toRestore.getCurrentProc().toString()+"\n\t Instruction "+ 
			toRestore.getCurrInstruction()+ " R1: "+toRestore.getRegister1()+" R2: "+toRestore.getRegister2()+" R3: "
					+toRestore.getRegister3()+ " R4: "+toRestore.getRegister4());
}

private static void contextSwitchSave(ProcessControlBlock currentPCB, SimProcessor processor, ArrayList<ProcessControlBlock>blockedList) {
	currentPCB.setCurrInstruction(processor.getCurrInstruction()+1);
	currentPCB.setRegister1(processor.getRegisterValue());
	currentPCB.setRegister2(processor.getRegisterValue());
	currentPCB.setRegister3(processor.getRegisterValue());
	currentPCB.setRegister4(processor.getRegisterValue());
	System.out.println("***Context Switch: saving process "+currentPCB.getCurrentProc().toString()+"\n\t Instruction "+ 
	currentPCB.getCurrInstruction()+ " R1: "+currentPCB.getRegister1()+" R2: "+currentPCB.getRegister2()+" R3: "
			+currentPCB.getRegister3()+ " R4: "+currentPCB.getRegister4());
	blockedList.add(currentPCB);
}
	
}

