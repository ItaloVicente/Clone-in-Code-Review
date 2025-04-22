	private void updateHeadMenu(Object newValue) {
		if (headMenuManager == null) {
			return;
		}
		boolean isEmpty = !(newValue instanceof String)
				|| ((String) newValue).trim().isEmpty();
		boolean managerEmpty = headMenuManager.isEmpty();
		if (managerEmpty != isEmpty) {
			IWorkbenchPartSite site = getSite();
			Shell shell = site != null ? site.getShell() : null;
			Display display = shell != null ? shell.getDisplay() : null;
			if (display != null && !display.isDisposed()) {
				display.asyncExec(() -> {
					if (display.isDisposed() || headMenuManager == null) {
						return;
					}
					if (managerEmpty) {
						headMenuManager.add(new Action() {
						});
					} else {
						headMenuManager.removeAll();
					}
					formHead.layout();
				});
			}
		}
	}

