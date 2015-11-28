package pipeline;

import java.util.ArrayList;

public class Pipeline {
	private static ArrayList<PipelineStage> pipeline = new ArrayList<PipelineStage>();

	/**
	 * @return the pipeline
	 */
	public static ArrayList<PipelineStage> getPipeline() {
		return pipeline;
	}

	/**
	 * @param pipeline the pipeline to set
	 */
	public static void setPipeline(ArrayList<PipelineStage> pipeline) {
		Pipeline.pipeline = pipeline;
	}
}
