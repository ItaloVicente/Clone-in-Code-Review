				getShell().addTraverseListener(e -> {
					if (e.detail == SWT.TRAVERSE_RETURN || (e.detail == SWT.TRAVERSE_MNEMONIC && e.keyCode == 32)) {
						if (timeWhenLastJobFinished != 0 && System.currentTimeMillis() - timeWhenLastJobFinished < RESTORE_ENTER_DELAY) {
							e.doit= false;
							return;
						}
						timeWhenLastJobFinished= 0;
					}});
