			@Override
			protected void adjustBounds() {
				Display display = getShell().getDisplay();
				if (display != null && label != null) {
					Rectangle clientArea = display.getClientArea();
					if (clientArea != null && clientArea.width > 0) {
						int workbenchWidth = clientArea.width;
						int currentHeight = label.getSize().y;
						int preferredHeight = label.computeSize(workbenchWidth, SWT.DEFAULT).y;
						if (currentHeight != preferredHeight) {
							GridData data = (GridData) label.getLayoutData();
							data.heightHint = preferredHeight;
							getShell().pack();
						}
					}
				}
			}

