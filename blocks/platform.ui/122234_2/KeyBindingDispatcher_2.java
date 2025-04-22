			if (isTracingEnabled() && !Character.isLetterOrDigit(event.character)) {
				if (eatKey) {
					logger.trace("Event processing done for: " + keyStrokes + " in context " + context); //$NON-NLS-1$//$NON-NLS-2$
				} else {
					logger.trace("Event processing forwarded for: " + keyStrokes + " in context " + context); //$NON-NLS-1$//$NON-NLS-2$
				}
			}
