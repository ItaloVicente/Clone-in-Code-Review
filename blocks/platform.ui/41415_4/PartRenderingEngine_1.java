		} else {
			if (changedElement.getWidget() instanceof Control) {
				Control ctrl = (Control) changedElement.getWidget();

				if (!(ctrl instanceof Shell)) {
					ctrl.getShell().layout(new Control[] { ctrl }, SWT.DEFER);
				}

				ctrl.setParent(getLimboShell());
			}

			if (parent instanceof MElementContainer<?>) {
				renderer.hideChild((MElementContainer<MUIElement>) parent, changedElement);
			}
