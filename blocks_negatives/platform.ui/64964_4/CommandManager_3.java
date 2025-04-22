				final Object[] listeners = executionListeners.getListeners();
				for (int i = 0; i < listeners.length; i++) {
					final Object object = listeners[i];
					if (object instanceof IExecutionListener) {
						final IExecutionListener listener = (IExecutionListener) object;
						listener.preExecute(commandId, event);
					}
