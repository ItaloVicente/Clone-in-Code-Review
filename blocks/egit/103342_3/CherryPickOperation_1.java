
				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_CherryPick, CoreText.End_CherryPick,
						CoreText.Error_CherryPick,
						new String[] { commit.name(),
								OperationLogger.getCurrentBranch(repo) });
				opLogger.logStart();
