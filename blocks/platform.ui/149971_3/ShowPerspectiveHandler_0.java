			Object[] found = new Object[1];
			BusyIndicator.showWhile(null, () -> {
				try {
					if (newWindow == null || newWindow.equalsIgnoreCase("false")) { //$NON-NLS-1$
						openPerspective((String) value, window);
					} else {
						openNewWindowPerspective((String) value, window);
					}
				} catch (ExecutionException e) {
					found[0] = e;
				}
			});
			if (found[0] instanceof ExecutionException) {
				throw ((ExecutionException) found[0]);
