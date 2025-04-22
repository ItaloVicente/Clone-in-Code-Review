		messageSection.setPreviousCommitterKey(COMMITTER_VALUES_PREF);
		messageSection.setPreviousAuthorKey(AUTHOR_VALUES_PREF);
		messageSection.createControl(container, toolkit, true);
		messageSection.getMessageArea().getTextWidget()
				.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent event) {
						if (event.keyCode == SWT.CR
								&& (event.stateMask & SWT.CONTROL) > 0) {
							okPressed();
						} else if (event.keyCode == SWT.TAB
								&& (event.stateMask & SWT.SHIFT) == 0) {
							event.doit = false;
							messageSection.getMessageArea().getTextWidget()
									.traverse(SWT.TRAVERSE_TAB_NEXT);
						}
