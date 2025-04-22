			@Override
			public void shellActivated(ShellEvent e) {
				newShell.removeShellListener(this); // Only the first time
				if (minimumSize != null) {
					newShell.setMinimumSize(minimumSize);
				}
			}
		});
