				Object w = createGui(changedElement);
				if (w instanceof Control && !(w instanceof Shell)) {
					fixZOrder(changedElement);
				}
			} else {
				Activator
						.trace(Policy.DEBUG_RENDERER, "visible -> false", null); //$NON-NLS-1$
