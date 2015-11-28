package pipeline;

import java.util.ArrayList;

public class Pipeline {
	public static ArrayList<Pipeline> pipeline = new ArrayList<Pipeline>();
	public ArrayList<String> stages = new ArrayList<String>();

	/**
	 * @return the pipeline
	 */
	public static ArrayList<Pipeline> getPipeline() {
		return pipeline;
	}

	/**
	 * @param pipeline the pipeline to set
	 */
	public static void setPipeline(ArrayList<Pipeline> pipeline) {
		Pipeline.pipeline = pipeline;
	}
}
