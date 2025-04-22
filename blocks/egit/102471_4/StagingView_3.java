	private void assumeUnchanged(@NonNull IStructuredSelection selection) {
		List<IPath> locations = new ArrayList<>();
		collectPaths(selection.toList(), locations);

		if (locations.isEmpty()) {
			return;
		}

		JobUtil.scheduleUserJob(
				new AssumeUnchangedOperation(currentRepository, locations, true),
				UIText.AssumeUnchanged_assumeUnchanged,
				JobFamilies.ASSUME_NOASSUME_UNCHANGED);
	}

	private void untrack(@NonNull IStructuredSelection selection) {
		List<IPath> locations = new ArrayList<>();
		collectPaths(selection.toList(), locations);

		if (locations.isEmpty()) {
			return;
		}

		JobUtil.scheduleUserJob(
				new UntrackOperation(currentRepository, locations),
				UIText.Untrack_untrack, JobFamilies.UNTRACK);
	}

	private void collectPaths(Object o, List<IPath> result) {
		if (o instanceof Iterable<?>) {
			((Iterable<?>) o).forEach(child -> collectPaths(child, result));
		} else if (o instanceof StagingFolderEntry) {
			collectPaths(Arrays.asList(((StagingFolderEntry) o).getChildren()),
					result);
		} else if (o instanceof StagingEntry) {
			result.add(AdapterUtils.adapt(o, IPath.class));
		}
	}

