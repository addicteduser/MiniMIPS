package pipeline;

import java.util.ArrayList;

import dataObjects.Instruction;
import dataObjects.MemoryInstruction;

public class PipelineMapGenerator {
	private boolean isFirstInstruction = true;
	private ArrayList<String> tempPipeline;
	
	public void generateMap() {
		for (Instruction i : MemoryInstruction.getInstructionList()) {
			tempPipeline = new ArrayList<String>();
			if (isFirstInstruction) {
				tempPipeline.add("IF");
				tempPipeline.add("ID");	
				tempPipeline.add("EX");	
				tempPipeline.add("MEM");	
				tempPipeline.add("WB");
				//Pipeline.getPipeline().
			}
		}
	}
}
