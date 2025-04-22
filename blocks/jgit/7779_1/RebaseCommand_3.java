	public RebaseCommand runInteractliveley(InteractiveHandler handler) {
		this.interactiveHandler = handler;
		return this;
	}

	public interface InteractiveHandler {
		void prepareSteps(List<Step> steps);

		String modifyCommitMessage(String commit);
	}

	public static enum Action {
		PICK("pick"
		REWORD("reword"
