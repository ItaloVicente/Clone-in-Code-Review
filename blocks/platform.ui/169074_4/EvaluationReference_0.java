				String message = "Failed to evaluate: " + expression; //$NON-NLS-1$
				logThrottle2.log(ERROR.ordinal(), message, e);
				boolean logged = logThrottle.log(ERROR.ordinal(), message, e);
				if (!logged && Policy.DEBUG_CMDS) {
					Activator.trace(Policy.DEBUG_CMDS_FLAG, message, e);
				}
