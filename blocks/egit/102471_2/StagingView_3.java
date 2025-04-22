	private void assumeUnchanged(@NonNull IStructuredSelection selection) {
		IPath[] locations = SelectionUtils.getSelectedLocations(selection);
		if (locations.length == 0) {
			return;
		}

		JobUtil.scheduleUserJob(
				new AssumeUnchangedOperation(currentRepository, Arrays.asList(locations), true),
				UIText.AssumeUnchanged_assumeUnchanged,
				JobFamilies.ASSUME_NOASSUME_UNCHANGED);
	}

	private void untrack(@NonNull IStructuredSelection selection) {
		IPath[] locations = SelectionUtils.getSelectedLocations(selection);
		if (locations.length == 0) {
			return;
		}

		JobUtil.scheduleUserJob(
				new UntrackOperation(currentRepository, Arrays.asList(locations)),
				UIText.Untrack_untrack, JobFamilies.UNTRACK);
	}

