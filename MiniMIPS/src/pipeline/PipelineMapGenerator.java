package pipeline;

import java.util.ArrayList;

import constants.INSTRUCTIONS;
import constants.STAGES;
import dataObjects.Instruction;
import dataObjects.MemoryInstruction;

public class PipelineMapGenerator {
	private PipelineStage tempPipelineStage;
	private int pointer = 1;
	private Instruction current;
	private Instruction previous;
	private ArrayList<Integer> dependencies = new ArrayList<Integer>(); 
	private String rd;
	private String rs;
	private String rt;

	public void generateMap() {
		for (int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			int stage = 1;
			tempPipelineStage = new PipelineStage();
			current = MemoryInstruction.getInstructionList().get(i);

			if (i == 0) { // first instruction
				while (stage <= 5) {
					addStage(stage);
					stage++;
				}

				tempPipelineStage.setClockCycleStart(pointer);
				Pipeline.getPipeline().add(tempPipelineStage);
			} else {
				previous = MemoryInstruction.getInstructionList().get(i - 1);
				
				// check for dependencies from previous instructions
				for (int j = i; j > 0; j--) {
					Instruction prev = MemoryInstruction.getInstructionList().get(j);
					
					if(checkDependencies(prev, current))
						dependencies.add(j);
				}
				
				// add the IF delays
				for (int k = 0; k < i; k++) {
					addStage(0);
				}
		
				setValues();
				
				if (current.getInstructionName().toString().matches("BEQ|J")) { // handle branches
					
					
					
					
					// di ko na alam pano implement to, sobrang nasabaw na ako at this point. sorry. :(
					
					
					
					
				} else {
					int clock = 0;
					while (stage <= 5) {
						// check if STALL due to STALL from previous instruction within the same clock cycle
						ArrayList<STAGES> prevStage = previous.getPipelineStage().getStages();
						int prevStagSize = prevStage.size();
						
						if(clock < prevStagSize) {
							if(prevStage.get(clock).equals(STAGES.STALL)) {
								addStage(0);
							}
						}
						
						// check if EX or onwards
						if(stage >= 3) {
							// if there are no dependencies, complete the 5 stages
							if(dependencies.size() == 0) {
								addStage(stage);
								stage++;
							} else {
								// for each dependence check if it should stall or not
								for(int dep : dependencies) {
									Instruction temp = MemoryInstruction.getInstructionList().get(dep);
									ArrayList<STAGES> tempStage = temp.getPipelineStage().getStages();
									int tempStagSize = tempStage.size();
									
									if(clock < tempStagSize) {
										if(temp.getInstructionName().equals(INSTRUCTIONS.LS) ||
												temp.getInstructionName().equals(INSTRUCTIONS.LW) ||
												temp.getInstructionName().equals(INSTRUCTIONS.LWU)) { // check if STALL due to LOAD
											if(tempStage.get(clock).equals(STAGES.MEM))
												addStage(0);
											else {
												addStage(stage);
												stage++;
											}
											
										} else { // check if STALL due to OTHERS
											if(tempStage.get(clock).equals(STAGES.EX))
												addStage(0);
											else {
												addStage(stage);
												stage++;
											}
											
										}
									}
								}
							}
						} else {
							addStage(stage);
							stage++;
						}
						
						clock++;
					}
				}
			}
			
			pointer++;
			current.setPipelineStage(tempPipelineStage);
		}
	}
	
	private boolean checkDependencies(Instruction prev, Instruction curr) {
		String pRD = "";
		String cRS = "";
		String cRT = "";
		
		pRD = prev.getV1();
		if(current.getInstructionType().toString().matches("R|ER")) {
			cRS = curr.getV2();
			cRT = curr.getV3();
		} else if (curr.getInstructionType().toString().matches("RS|I")) {
			if(curr.getInstructionName().toString().matches("DSLL|BEQ|ANDI|DADDIU")) {
				cRS = curr.getV2();
			}
		}
		
		if(pRD.matches(cRS) || pRD.matches(cRT))
			return true;
		
		return false;
	}

	private void addStage(int stage) {
		switch (stage) {
		case 1:
			tempPipelineStage.getStages().add(STAGES.IF);
			break;
		case 2:
			tempPipelineStage.getStages().add(STAGES.ID);
			break;
		case 3:
			tempPipelineStage.getStages().add(STAGES.EX);
			break;
		case 4:
			tempPipelineStage.getStages().add(STAGES.MEM);
			break;
		case 5:
			tempPipelineStage.getStages().add(STAGES.WB);
			break;
		default:
			tempPipelineStage.getStages().add(STAGES.STALL);
		}
	}
	
	private void setValues() {
		rd = previous.getV1();
		
		if(current.getInstructionType().toString().matches("R|ER")) {
			rs = current.getV2();
			rt = current.getV3();
		} else if (current.getInstructionType().toString().matches("RS|I")) {
			if(current.getInstructionName().toString().matches("DSLL|BEQ|ANDI|DADDIU")) {
				rs = current.getV2();
			}
		}
	}
}
