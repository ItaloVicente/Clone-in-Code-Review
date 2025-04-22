			} else if ((keyAssistDialog != null)
					&& (keyAssistDialog.getShell() != null)
					&& ((event.keyCode == SWT.ARROW_DOWN) || (event.keyCode == SWT.ARROW_UP)
							|| (event.keyCode == SWT.ARROW_LEFT)
							|| (event.keyCode == SWT.ARROW_RIGHT) || (event.keyCode == SWT.CR)
							|| (event.keyCode == SWT.PAGE_UP) || (event.keyCode == SWT.PAGE_DOWN))) {
				return false;
