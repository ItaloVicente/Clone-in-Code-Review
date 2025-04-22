					OperationLogger opLogger = new OperationLogger(
							CoreText.Start_Push, CoreText.End_Push,
							CoreText.Error_Push,
							new String[] {
									uri == null ? "null" : uri.toString(), //$NON-NLS-1$
									OperationLogger.getBranch(localDb),
									OperationLogger.getPath(localDb) });
					opLogger.logStart();
