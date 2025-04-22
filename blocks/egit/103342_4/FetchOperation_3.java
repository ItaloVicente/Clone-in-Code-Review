			OperationLogger opLogger = new OperationLogger(CoreText.Start_Fetch,
					CoreText.End_Fetch, CoreText.Error_Fetch,
					new String[] {
							rc == null ? uri.toPrivateString() : rc.getName(),
							OperationLogger.getBranch(repository),
							OperationLogger.getPath(repository) });
			opLogger.logStart();
