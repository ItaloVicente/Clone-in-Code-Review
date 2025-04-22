				if (commandException instanceof ExecutionException) {
					if (logger != null) {
						logger.error((Throwable) commandException,
								"Execution exception for: " + parameterizedCommand + " in context: " + context); //$NON-NLS-1$//$NON-NLS-2$
					}
				} else {
					if (isTracingEnabled()) {
						logger.trace((Throwable) commandException,
								"Command exception for: " + parameterizedCommand + " in context: " + context); //$NON-NLS-1$ //$NON-NLS-2$
					}
