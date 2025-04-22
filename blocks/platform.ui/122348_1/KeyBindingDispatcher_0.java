			if (oldState && !enabled && isTracingEnabled()) {
				logger.trace(new Exception("Probably illegal method call (except for very few cases)"), //$NON-NLS-1$
						"KeyBindingDispatcher is DISABLED!"); //$NON-NLS-1$
			}
			if (!oldState && enabled && isTracingEnabled()) {
				logger.trace("KeyBindingDispatcher is ENABLED!"); //$NON-NLS-1$
			}
