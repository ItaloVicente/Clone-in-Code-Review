		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				part.setFocus();
				proceed[0] = MessageDialog.openQuestion(part.getSite()
						.getShell(), getTitle(part), message);
			}
