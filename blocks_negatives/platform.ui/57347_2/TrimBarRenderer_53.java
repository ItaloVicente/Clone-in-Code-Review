			trimComposite.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(trimModel);
				}
			});
