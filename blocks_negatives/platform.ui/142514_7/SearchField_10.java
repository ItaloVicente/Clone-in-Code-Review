		parent.getShell().addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				closeDropDown();
			}

			@Override
			public void controlMoved(ControlEvent e) {
				closeDropDown();
			}

			private void closeDropDown() {
				if (shell == null || shell.isDisposed() || txtQuickAccess.isDisposed() || !shell.isVisible())
					return;
				quickAccessContents.doClose();
			}
		});

		hookUpSelectAll();

