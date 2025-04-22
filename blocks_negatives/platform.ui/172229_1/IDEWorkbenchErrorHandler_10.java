				if (t.getMessage() == null) {
					msg = IDEWorkbenchMessages.InternalErrorNoArg;
				} else {
					msg = NLS.bind(IDEWorkbenchMessages.InternalErrorOneArg, t
							.getMessage());
				}
