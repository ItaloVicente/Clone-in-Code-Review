				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Squash, CoreText.End_Squash,
						CoreText.Error_Squash,
						new String[] {
								OperationLogger.getBranch(repository),
								OperationLogger.getPath(repository),
								commits.get(0).getParent(0).name() });
				opLogger.logStart();
