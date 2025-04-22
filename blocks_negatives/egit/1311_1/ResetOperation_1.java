		}
		monitor.worked(1);

		if (type != ResetType.SOFT) {
			refreshIndex();
		}
		monitor.worked(1);

		monitor.worked(1);

		if (type == ResetType.HARD)
