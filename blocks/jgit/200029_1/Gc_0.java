			GarbageCollectCommand command = git.gc().setAggressive(aggressive)
					.setProgressMonitor(new TextProgressMonitor(errw));
			if (preserveOldPacks != null) {
				command.setPreserveOldPacks(preserveOldPacks.booleanValue());
			}
			if (prunePreserved != null) {
				command.setPrunePreserved(prunePreserved.booleanValue());
			}
			command.call();
