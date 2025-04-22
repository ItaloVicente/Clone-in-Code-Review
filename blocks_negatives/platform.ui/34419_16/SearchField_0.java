		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				quickAccessContents.doClose();
				e.doit = false;
			}
		});
