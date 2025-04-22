				Menu mainMenu = (Menu) model.getMainMenu().getWidget();
				if (mainMenu != null && !mainMenu.isDisposed() && mainMenu.isEnabled()) {
					mainMenu.setEnabled(false);
					enableMainMenu = true;
				}				
				
				Shell theShell = getShell();
				for (Shell childShell : theShell.getShells()) {
					disableControl(childShell, toEnable);
				}
				
				TrimmedPartLayout tpl = (TrimmedPartLayout) getShell().getLayout();
				disableControl(tpl.clientArea, toEnable);
				disableControl(tpl.top, toEnable);
				disableControl(tpl.left, toEnable);
				disableControl(tpl.right, toEnable);
				
				if (tpl.bottom != null && !tpl.bottom.isDisposed() && tpl.bottom.isEnabled()) {
					MUIElement statusLine = modelService.find("StatusLine", model); //$NON-NLS-1$
					if (statusLine != null && statusLine.getWidget() instanceof Control) {
						Control slCtrl = (Control) statusLine.getWidget();
						for (Control bottomCtrl : tpl.bottom.getChildren()) {
							if (bottomCtrl != slCtrl)
								disableControl(bottomCtrl, toEnable);
						}		
					}
				}

