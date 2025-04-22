		if (active) {
			try {
				if (handler instanceof IHandler2) {
					((IHandler2) handler).setEnabled(context);
				}
				if (!handler.isEnabled()) {
					active = false;
				}
			} catch (RuntimeException e) {
				active = false;
				Activator.trace(Policy.DEBUG_CMDS_FLAG, "Failed to evaluate handler active state for HandlerActivation " //$NON-NLS-1$
						+ this + " in context " + context, e); //$NON-NLS-1$
			}
		}
