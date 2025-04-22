		IResource[] resources = getSelectedResources(event);
		if (resources.length == 1)
			JobUtil.scheduleUserJob(
					new CompareWithPreviousOperation(event, repository,
							resources[0]),
					UIText.CompareWithPreviousActionHandler_TaskGeneratingInput,
					null);
		return null;
	}
