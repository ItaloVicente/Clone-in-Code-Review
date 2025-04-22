			SubMonitor progress = SubMonitor.convert(monitor, 3);
			List<DiffEntry> entries = DiffEntry.scan(walk, false,
					markTreeFilters);
			if (progress.isCanceled()) {
				return new FileDiff[0];
			}
			progress.worked(1);
