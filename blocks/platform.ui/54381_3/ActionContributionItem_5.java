		proxy.getDisplay().asyncExec(() -> {
			if (!proxy.isDisposed()) {
				MenuItem parentItem = proxy.getParentItem();
				proxy.dispose();
				parentItem.setMenu(holdMenu);
			}
			if (holdMenu != null && !holdMenu.isDisposed()) {
				holdMenu.notifyListeners(SWT.Hide, null);
