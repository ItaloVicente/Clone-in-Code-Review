		try {
			toolBar.setRedraw(false);

			for (int i = toRemove.size(); --i >= 0;) {
				ToolItem item = toRemove.get(i);
				if (!item.isDisposed()) {
					Control ctrl = item.getControl();
					if (ctrl != null) {
						item.setControl(null);
						ctrl.dispose();
					}
					item.dispose();
