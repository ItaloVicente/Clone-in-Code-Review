			newMenu.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(menuModel);
					MenuManager manager = getManager(menuModel);
					if (manager != null) {
						manager.markDirty();
					}
