		IWorkspaceRunnable action = actMonitor -> {
			String taskName = NLS.bind(
					CoreText.CreateLocalBranchOperation_CreatingBranchMessage,
					name);
			SubMonitor progress = SubMonitor.convert(actMonitor);
			progress.setTaskName(taskName);
			try (Git git = new Git(repository)) {
				if (ref != null) {
					SetupUpstreamMode mode;
					if (upstreamConfig == null)
						mode = SetupUpstreamMode.NOTRACK;
