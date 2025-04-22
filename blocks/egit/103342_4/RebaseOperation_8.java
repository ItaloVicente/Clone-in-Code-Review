				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Rebase, CoreText.End_Rebase,
						CoreText.Error_Rebase,
						new String[] {
								OperationLogger.getBranch(repository),
								OperationLogger.getPath(repository),
								ref == null ? "null" : ref.getName() }); //$NON-NLS-1$
				opLogger.logStart();
