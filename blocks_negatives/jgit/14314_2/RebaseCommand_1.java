	/**
	 * Allows configure rebase interactive process and modify commit message
	 */
	public interface InteractiveHandler {
		/**
		 * Given list of {@code steps} should be modified according to user
		 * rebase configuration
		 * @param steps
		 *            initial configuration of rebase interactive
		 */
		void prepareSteps(List<Step> steps);

		/**
		 * Used for editing commit message on REWORD
		 *
		 * @param commit
		 * @return new commit message
		 */
		String modifyCommitMessage(String commit);
	}

