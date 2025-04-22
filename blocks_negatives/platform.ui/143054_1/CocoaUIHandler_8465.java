
		if (OS.VERSION >= 0x1070) {
			return;
		}
		boolean trimInitiallyVisible = false;
		if (window instanceof MTrimmedWindow && !((MTrimmedWindow) window).getTrimBars().isEmpty()) {
			for (MTrimBar tb : ((MTrimmedWindow) window).getTrimBars()) {
				if (tb.isVisible()) {
					trimInitiallyVisible = true;
				}
			}
		}

		if (trimInitiallyVisible) {
			Shell shell = ((Control) window.getWidget()).getShell();
			NSWindow nsWindow = shell.view.window();
			NSToolbar dummyBar = new NSToolbar();
			dummyBar.alloc();
			dummyBar.setVisible(false);
			nsWindow.setToolbar(dummyBar);
			dummyBar.release();
			nsWindow.setShowsToolbarButton(true);

			try {
				Object fieldValue = wrapPointer(NSWindowToolbarButton);
				NSButton toolbarButton = (NSButton) invokeMethod(NSWindow.class, nsWindow, "standardWindowButton", //$NON-NLS-1$
						new Object[] { fieldValue });
				if (toolbarButton != null) {
					toolbarButton.setTarget(delegate);
					invokeMethod(NSControl.class, toolbarButton, "setAction", //$NON-NLS-1$
							new Object[] { wrapPointer(sel_toolbarButtonClicked_) });
				}
			} catch (Exception e) {
				log(e);
			}
		}
