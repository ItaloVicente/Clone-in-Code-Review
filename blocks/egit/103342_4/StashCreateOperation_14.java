				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Stash_Create, CoreText.End_Stash_Create,
						CoreText.Error_Stash_Create, new String[] {
								OperationLogger.getBranch(repository),
								OperationLogger.getPath(repository) });
				opLogger.logStart();
