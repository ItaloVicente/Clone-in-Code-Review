		if (!needsRecompute) {
			return diff != null ? diff : new Object[0];
		}
		needsRecompute = false;
		FileDiffInput input = currentInput;
		if (input == null) {
			diff = null;
			return new Object[0];
		}
		cancel();
		FileDiffLoader job = new FileDiffLoader(input, markTreeFilter);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!event.getResult().isOK()) {
					return;
				}
				UIJob updater = new UpdateJob(MessageFormat.format(
						UIText.FileDiffContentProvider_updatingFileDiffs,
						input.getCommit().getName()), job);
				updater.schedule();
