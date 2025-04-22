		boolean cancel = MessageDialog.openQuestion(getShell(),
				IDEWorkbenchMessages.GlobalBuildAction_BuildRunningTitle,
				IDEWorkbenchMessages.GlobalBuildAction_BuildRunningMessage);
		if (cancel) {
			for (Job job : buildJobs) {
				job.cancel();
			}
		}
		return false;
	}
