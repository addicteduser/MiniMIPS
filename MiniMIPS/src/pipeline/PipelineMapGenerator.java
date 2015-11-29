package pipeline;

import constants.STAGES;
import dataObjects.Instruction;
import dataObjects.MemoryInstruction;

public class PipelineMapGenerator {
	private PipelineStage tempPipelineStage;
	private int pointer = 1;
	private Instruction current;
	private Instruction previous;
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
				current.setPipelinestage(tempPipelineStage);
			} else {
				setValues();
				if (current.getInstructionName().toString().matches("BEQ|J")) { // handle branches

				} else {
					while (stage <= 5) {
						addStage(stage);
						stage++;
					}
				}
				
				tempPipelineStage.setClockCycleStart(pointer);
				current.setPipelinestage(tempPipelineStage);
			}
			previous = current;
			pointer++;
		}
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

	private boolean isDataHazard() {
		if(rd.matches(rs) || rd.matches(rt))
			return false;
		return true;
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
