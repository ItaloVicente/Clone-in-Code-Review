					OperationLogger opLogger = new OperationLogger(
							CoreText.Start_Pull, CoreText.End_Pull,
							CoreText.Error_Pull,
							new String[] {
									OperationLogger.getBranch(repository),
									OperationLogger.getPath(repository) });
					opLogger.logStart();
