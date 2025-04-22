		Activator.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					populate();
				} catch (Exception e1) {
					Activator.logError(UIText.GitDocument_errorRefreshQuickdiff, e1);
				}
			}
		});
