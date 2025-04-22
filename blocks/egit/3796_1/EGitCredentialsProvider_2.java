		final boolean[] result = new boolean[1];
		
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				
				if (items.length == 1) {
					CredentialItem item = items[0];
					result[0] = getSingleSpecial(shell, uri, item);
				} else {
					result[0] = getMultiSpecial(shell, uri, items);
				}
			}
		});
