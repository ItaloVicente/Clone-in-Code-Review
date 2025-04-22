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
				Activator.trace(Policy.DEBUG_CMDS_FLAG, "Failed to calculate active", e); //$NON-NLS-1$
			}
		}
