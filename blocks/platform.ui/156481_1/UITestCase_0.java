
		List<String> leakedModalShellTitles = new ArrayList<>();
		Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
		for (Shell shell : shells) {
			if (!shell.isDisposed() && shell.isVisible()
					&& (shell.getStyle() & (SWT.APPLICATION_MODAL | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL)) != 0) {
				leakedModalShellTitles.add(shell.getText());
				shell.close();
			}
		}
		assertEquals("Test leaked modal shell: [" + String.join(", ", leakedModalShellTitles) + "]", 0,
				leakedModalShellTitles.size());
