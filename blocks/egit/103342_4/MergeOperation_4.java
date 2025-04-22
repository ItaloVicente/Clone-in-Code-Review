				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Merge, CoreText.End_Merge,
						CoreText.Error_Merge, new String[] { refName,
								OperationLogger.getBranch(repository),
								OperationLogger.getPath(repository) });
				opLogger.logStart();
