package processingSchedulingSim;

import java.util.ArrayList;
import java.util.Random;

public class Main {
public static void main(String[]args) {
	final int QUANTUM=5;
	SimProcessor processor=new SimProcessor();
	ArrayList<ProcessControlBlock>readyList= new ArrayList<ProcessControlBlock>();
	ArrayList<ProcessControlBlock>blockedList= new ArrayList<ProcessControlBlock>();
	ArrayList<ProcessControlBlock>finishedList= new ArrayList<ProcessControlBlock>();
	fillLists(readyList, blockedList);
	int instructionIterator=0;
	for(int x=0; x<2999;x++) {
		System.out.print("Step "+(x+1)+": ");
		if(!readyList.isEmpty()) {
			ProcessControlBlock currentPCB= readyList.get(0);
			processor.setCurrentProcess(currentPCB.getCurrentProc());
			processor.setCurrInstruction(currentPCB.getCurrInstruction());
			ProcessState state= processor.executeNextInstruction();
			switch (state){
			case FINISHED:
				System.out.println("Process "+currentPCB.getCurrentProc().toString()+" finished.");
				finishedList.add(currentPCB);
				readyList.remove(currentPCB);
				x++;
				System.out.print("Step "+(x+1)+": ");
				contextSwitch(processor, blockedList, readyList, currentPCB, state, finishedList);
				instructionIterator=0;
				break;
			case READY:
				instructionIterator++;
				currentPCB.setCurrInstruction(processor.getCurrInstruction());
				if(instructionIterator==QUANTUM) {
					System.out.println("***Quantum Expired***");
					readyList.add(currentPCB);
					readyList.remove(currentPCB);
					x++;
					System.out.print("Step "+(x+1)+": ");
					contextSwitch(processor, blockedList, readyList, currentPCB, state, finishedList);
					instructionIterator=0;
				}
				break;
			case BLOCKED:
				System.out.println("***Process Blocked***");
				blockedList.add(currentPCB);
				readyList.remove(currentPCB);
				x++;
				System.out.print("Step "+(x+1)+": ");
				contextSwitch(processor, blockedList, readyList, currentPCB, state, finishedList);
				instructionIterator=0;
				break;
			default:
				break;
			}
		}
		else {
			if(blockedList.isEmpty()) {
				System.out.println("Processor is idling. Program Complete");
			}
			else {
				System.out.println("Processor is idling");
			}
		}
			wakeupProcess(blockedList, readyList);
		}
	System.exit(0);
	}
private static void contextSwitch(SimProcessor processor, ArrayList<ProcessControlBlock> blockedList,
	ArrayList<ProcessControlBlock> readyList, ProcessControlBlock currentPCB, ProcessState state, ArrayList<ProcessControlBlock> finishedList) {
	
	currentPCB.setCurrInstruction(processor.getCurrInstruction());
	currentPCB.setRegister1(processor.getRegister1());
	currentPCB.setRegister2(processor.getRegister2());
	currentPCB.setRegister3(processor.getRegister3());
	currentPCB.setRegister4(processor.getRegister4());
	System.out.println("\t***Context Switch: saving process "+currentPCB.getCurrentProc().toString()+"\n\t Instruction "+ 
	processor.getCurrInstruction()+ " R1: "+currentPCB.getRegister1()+" R2: "+currentPCB.getRegister2()+" R3: "
			+currentPCB.getRegister3()+ " R4: "+currentPCB.getRegister4());
	if(readyList.isEmpty()) {
		System.out.println("Processor is idling");
		wakeupProcess(blockedList, readyList);
	}
	else {
		ProcessControlBlock toRestore=readyList.get(0);
		restore(toRestore);
	}
}

private static void wakeupProcess(ArrayList<ProcessControlBlock> blockedList,
		ArrayList<ProcessControlBlock> readyList) {
	Random randomGen= new Random();
	for(int y=0; y<blockedList.size(); y++) {
		if(randomGen.nextDouble()<.3) {
			readyList.add(blockedList.get(y));
			blockedList.remove(y);
		}
	}
	
}

private static void restore(ProcessControlBlock toRestore) {
	System.out.println("\t***Context Switch: restoring process "+toRestore.getCurrentProc().toString()+"\n\t Instruction "+ 
			toRestore.getCurrInstruction()+ " R1: "+toRestore.getRegister1()+" R2: "+toRestore.getRegister2()+" R3: "
					+toRestore.getRegister3()+ " R4: "+toRestore.getRegister4());
}
private static void fillLists(ArrayList<ProcessControlBlock> readyList, ArrayList<ProcessControlBlock>blockedList) {
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
	
	if(process1.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb1);
	}
	else {
		readyList.add(pcb1);
	}
	
	if(process2.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb2);
	}
	else {
		readyList.add(pcb2);
	}
	
	if(process3.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb3);
	}
	else {
		readyList.add(pcb3);
	}
	
	if(process4.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb4);
	}
	else {
		readyList.add(pcb4);
	}
	
	if(process5.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb5);
	}
	else {
		readyList.add(pcb5);
	}
	
	if(process6.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb6);
	}
	else {
		readyList.add(pcb6);
	}
	
	if(process7.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb7);
	}
	else {
		readyList.add(pcb7);
	}
	
	if(process8.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb8);
	}
	else {
		readyList.add(pcb8);
	}
	
	if(process9.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb9);
	}
	else {
		readyList.add(pcb9);
	}
	
	if(process10.execute(0)==ProcessState.BLOCKED) {
		blockedList.add(pcb10);
	}
	else {
		readyList.add(pcb10);
	}
}
	
}

