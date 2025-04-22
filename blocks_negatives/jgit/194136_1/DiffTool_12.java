			if (launchCompare) {
				switch (ent.getChangeType()) {
				case MODIFY:
					int ret = diffTools.compare(ent.getNewPath(),
							ent.getOldPath(), ent.getNewId().name(),
							ent.getOldId().name(), toolName, prompt, gui,
							trustExitCode);
					if (ret != 0) {
						throw die(MessageFormat.format(
								CLIText.get().diffToolDied, mergedFilePath));
