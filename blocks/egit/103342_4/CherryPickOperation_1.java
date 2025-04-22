
				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_CherryPick, CoreText.End_CherryPick,
						CoreText.Error_CherryPick,
						new String[] { commit.name(),
								OperationLogger.getBranch(repo),
								OperationLogger.getPath(repo) });
				opLogger.logStart();
