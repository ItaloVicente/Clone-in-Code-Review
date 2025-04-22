		OperationLogger opLogger = new OperationLogger(CoreText.Start_Fetch,
				CoreText.End_Fetch, CoreText.Error_Fetch,
				new String[] { remoteName,
						OperationLogger.getBranch(repository),
						OperationLogger.getPath(repository) });
		opLogger.logStart();
