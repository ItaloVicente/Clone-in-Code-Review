			break;
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell();
			new RebaseResultDialog(shell, repository, result).open();
