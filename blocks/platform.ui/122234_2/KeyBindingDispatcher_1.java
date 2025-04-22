						if (isTracingEnabled()) {
							logger.trace("Error matches for key: " + sequenceAfterKeyStroke + ", :" + errorMatches); //$NON-NLS-1$//$NON-NLS-2$
						}
					} else {
						if (isTracingEnabled() && !Character.isLetterOrDigit(event.character)) {
							logger.trace("No binding for keys: " + sequenceBeforeKeyStroke + " " //$NON-NLS-1$//$NON-NLS-2$
									+ sequenceAfterKeyStroke + " in context " + context); //$NON-NLS-1$
						}
