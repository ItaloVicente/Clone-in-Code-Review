				OperationLogger opLogger = new OperationLogger(
						CoreText.Start_Rebase, CoreText.End_Rebase,
						CoreText.Error_Rebase,
						new String[] {
								OperationLogger.getCurrentBranch(repository),
								ref == null ? "null" : ref.getName() }); //$NON-NLS-1$
				opLogger.logStart();
