				waitCursor = workbenchPart.getSite().getWorkbenchWindow().getShell().getDisplay()
						.getSystemCursor(SWT.CURSOR_WAIT);
			}
			if (waitCursor.equals(paneComposite.getCursor())) {
				originalCursor = paneComposite.getCursor();
				paneComposite.setCursor(waitCursor);
