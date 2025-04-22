		} else {
			if (Policy.DEBUG_FOCUS) {
				Activator.trace(Policy.DEBUG_FOCUS_FLAG,
						"Focus not set, object or context missing: " + object + ", " + context, //$NON-NLS-1$ //$NON-NLS-2$
						new IllegalStateException());
			}
