		OperationLogger opLogger = new OperationLogger(
				CoreText.Start_Reset, CoreText.End_Reset, CoreText.Error_Reset,
				new String[] { OperationLogger.getBranch(repository),
						OperationLogger.getPath(repository),
						refName });
		opLogger.logStart();
