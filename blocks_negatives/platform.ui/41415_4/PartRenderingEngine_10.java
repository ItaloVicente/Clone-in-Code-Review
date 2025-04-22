					if (removed.getWidget() instanceof Control) {
						Control ctrl = (Control) removed.getWidget();
						ctrl.setLayoutData(null);
						ctrl.getParent().layout(new Control[] { ctrl },
								SWT.CHANGED | SWT.DEFER);
					}
