package pipeline;

import java.util.ArrayList;

import constants.STAGES;

public class PipelineStage {
	private ArrayList<STAGES> stages = new ArrayList<STAGES>();
	private int clockCycleStart;

	/**
	 * @return the stages
	 */
	public ArrayList<STAGES> getStages() {
		return stages;
	}

	/**
	 * @param stages the stages to set
	 */
	public void setStages(ArrayList<STAGES> stages) {
		this.stages = stages;
	}

	/**
	 * @return the clockCycleStart
	 */
	public int getClockCycleStart() {
		return clockCycleStart;
	}

	/**
	 * @param clockCycleStart the clockCycleStart to set
	 */
	public void setClockCycleStart(int clockCycleStart) {
		this.clockCycleStart = clockCycleStart;
	}
}
