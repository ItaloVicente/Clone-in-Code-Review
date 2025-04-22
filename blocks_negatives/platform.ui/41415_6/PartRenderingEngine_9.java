			} else if (UIEvents.isREMOVE(event)) {
				Activator.trace(Policy.DEBUG_RENDERER, "Child Removed", null); //$NON-NLS-1$
				for (Object o : UIEvents.asIterable(event,
						UIEvents.EventTags.OLD_VALUE)) {
					MUIElement removed = (MUIElement) o;
					if (!removed.isToBeRendered())
						continue;
