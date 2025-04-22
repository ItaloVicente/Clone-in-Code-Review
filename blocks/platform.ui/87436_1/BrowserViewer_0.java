			if (showURLbar)
				createLocationBar(toolbarComp);

			if (showToolbar | showURLbar) {
				busy = new BusyIndicator(toolbarComp, SWT.NONE);
				busy.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				busy.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						setURL("http://www.eclipse.org"); //$NON-NLS-1$
					}
				});
			}
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, ContextIds.WEB_BROWSER);
		}
