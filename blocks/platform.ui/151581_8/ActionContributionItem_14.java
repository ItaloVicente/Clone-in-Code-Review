			if (callback != null) {
				if (result == null || result.equals(Boolean.TRUE)) {
					callback.postExecuteSuccess(action, Boolean.TRUE);
				} else {
					callback.postExecuteFailure(action,
							new ExecutionException(action.getText() + " returned failure.")); //$NON-NLS-1$
