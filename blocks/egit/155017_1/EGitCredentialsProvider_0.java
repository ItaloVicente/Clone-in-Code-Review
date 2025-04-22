		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			Shell shell = PlatformUI.getWorkbench()
					.getModalDialogShellProvider().getShell();

			if (items.length == 1) {
				CredentialItem item = items[0];
				result[0] = getSingleSpecial(shell, uri, item);
			} else {
				result[0] = getMultiSpecial(shell, uri, items);
