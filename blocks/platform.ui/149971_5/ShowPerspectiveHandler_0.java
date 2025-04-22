			ExecutionException[] exception = new ExecutionException[1];
			BusyIndicator.showWhile(null, () -> {
				try {
					if (newWindow == null || newWindow.equalsIgnoreCase("false")) { //$NON-NLS-1$
						openPerspective((String) value, window);
					} else {
						openNewWindowPerspective((String) value, window);
					}
				} catch (ExecutionException e) {
					exception[0] = e;
				}
			});
			if (exception[0] != null) {
				throw exception[0];
