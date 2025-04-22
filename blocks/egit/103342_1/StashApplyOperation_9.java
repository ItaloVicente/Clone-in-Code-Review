				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Stash_Apply, CoreText.End_Stash_Apply,
						CoreText.Error_Stash_Apply, new String[] {
								commit.name(),
								OperationLogger.getCurrentBranch(repository) });
				opLogger.logStart();
