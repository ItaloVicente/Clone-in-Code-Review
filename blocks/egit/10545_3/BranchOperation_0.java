						File projectDir = project.getLocation().toFile();
						closeMonitor.subTask(MessageFormat.format(
								CoreText.BranchOperation_cleaningMissingProject,
								project.getName()));
						project.build(IncrementalProjectBuilder.CLEAN_BUILD,
								closeMonitor);
