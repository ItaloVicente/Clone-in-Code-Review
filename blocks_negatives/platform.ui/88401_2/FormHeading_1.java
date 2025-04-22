			addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (toolBarManager != null) {
						toolBarManager.dispose();
						toolBarManager = null;
					}
