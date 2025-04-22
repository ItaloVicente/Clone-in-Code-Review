			} else {
				if (Policy.DEBUG_FOCUS) {
					WorkbenchSWTActivator.trace(Policy.DEBUG_FOCUS_FLAG,
							"Trying to force focus for disposed control: " + element, new IllegalStateException()); //$NON-NLS-1$
				}
			}
		} else {
			if (Policy.DEBUG_FOCUS) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_FOCUS_FLAG,
						"Trying to force focus for non-control element: " + element, new IllegalStateException()); //$NON-NLS-1$
			}
