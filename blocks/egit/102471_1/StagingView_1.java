	private void assumeUnchanged(@NonNull IStructuredSelection selection) {
		IResource[] selectedResources = SelectionUtils.getSelectedResources(selection);
		if (selectedResources.length == 0)
			return;
		JobUtil.scheduleUserJob(
				new AssumeUnchangedOperation(Arrays.asList(selectedResources), true),
				UIText.AssumeUnchanged_assumeUnchanged,
				JobFamilies.ASSUME_NOASSUME_UNCHANGED);
	}

	private void untrack(@NonNull IStructuredSelection selection) {
		IResource[] selectedResources = SelectionUtils.getSelectedResources(selection);
		if (selectedResources.length == 0)
			return;
		JobUtil.scheduleUserJob(new UntrackOperation(Arrays.asList(selectedResources)),
				UIText.Untrack_untrack, JobFamilies.UNTRACK);
	}

