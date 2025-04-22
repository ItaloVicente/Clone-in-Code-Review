		if (taskName == null || taskName.isEmpty()) {
			singleton.setBlockedTaskName(ProgressMessages.BlockedJobsDialog_UserInterfaceTreeElement);
		} else {
			singleton.setBlockedTaskName(taskName);
		}

