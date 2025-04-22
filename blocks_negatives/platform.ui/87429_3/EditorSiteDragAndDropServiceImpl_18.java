		newTarget.realDropTarget.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Object mdt = e.widget.getData(MDT_KEY);
				addedListeners.remove(mdt);
			}
