				int newLocation = modelService.getElementLocation(added);
				if (newLocation == EModelService.IN_SHARED_AREA || newLocation == EModelService.OUTSIDE_PERSPECTIVE) {
					MWindow topWin = modelService.getTopLevelWindowFor(added);
					modelService.hideLocalPlaceholders(topWin, null);
				}
			}
		} else if (UIEvents.isREMOVE(event)) {
			Activator.trace(Policy.DEBUG_RENDERER, "Child Removed", null); //$NON-NLS-1$
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MUIElement removed = (MUIElement) o;
				if (!removed.isToBeRendered())
					continue;

				if (removed.getWidget() instanceof Control) {
					Control ctrl = (Control) removed.getWidget();
					ctrl.setLayoutData(null);
					ctrl.getParent().layout(new Control[] { ctrl }, SWT.CHANGED | SWT.DEFER);
				}
