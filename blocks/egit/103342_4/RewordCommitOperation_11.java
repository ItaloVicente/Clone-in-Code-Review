				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Amend, CoreText.End_Amend,
						CoreText.Error_Amend,
						new String[] { commit.name(),
								OperationLogger.getBranch(repository),
								OperationLogger.getPath(repository) });
				opLogger.logStart();
