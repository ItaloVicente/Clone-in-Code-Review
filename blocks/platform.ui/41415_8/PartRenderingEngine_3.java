		if (UIEvents.isADD(event)) {
			Activator.trace(Policy.DEBUG_RENDERER, "Child Added", null); //$NON-NLS-1$
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement added = (MUIElement) o;

				boolean isStack = changedObj instanceof MGenericStack<?>;
				boolean hasWidget = added.getWidget() != null;
				boolean isSelected = added == changedElement.getSelectedElement();
				boolean renderIt = !isStack || hasWidget || isSelected;
				if (renderIt) {
					Object w = createGui(added);
					if (w instanceof Control && !(w instanceof Shell)) {
						final Control ctrl = (Control) w;
						fixZOrder(added);
						if (!ctrl.isDisposed()) {
							ctrl.getShell().layout(new Control[] { ctrl }, SWT.DEFER);
