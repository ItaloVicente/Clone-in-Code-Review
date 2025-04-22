
					if (!(ctrl instanceof Shell)) {
						ctrl.getShell().layout(new Control[] { ctrl },
								SWT.DEFER);
					}

					ctrl.setParent(getLimboShell());
