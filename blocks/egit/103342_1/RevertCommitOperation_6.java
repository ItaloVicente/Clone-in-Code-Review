				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Revert, CoreText.End_Revert,
						CoreText.Error_Revert, new String[] {
								commits.stream().map(i -> i.name())
										.collect(Collectors.joining(", ")), //$NON-NLS-1$
								OperationLogger.getCurrentBranch(repo) });
				opLogger.logStart();
