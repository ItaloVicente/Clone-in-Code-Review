	private void beginPhase(PackingPhase phase
			int cnt) {
		state.phase = phase;
		String task;
		switch (phase) {
		case COUNTING:
			task = JGitText.get().countingObjects;
			break;
		case GETTING_SIZES:
			task = JGitText.get().searchForSizes;
			break;
		case FINDING_SOURCES:
			task = JGitText.get().searchForReuse;
			break;
		case COMPRESSING:
			task = JGitText.get().compressingObjects;
			break;
		case WRITING:
			task = JGitText.get().writingObjects;
			break;
		default:
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().illegalPackingPhase
		}
		monitor.beginTask(task
	}

	private void endPhase(ProgressMonitor monitor) {
		monitor.endTask();
	}

