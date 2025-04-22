			Object[] found = new Object[1];
			BusyIndicator.showWhile(null, () -> {
				try {
					if (IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode && persp != null) {
						openNewWindowPerspective(perspectiveId, activeWorkbenchWindow);
					} else {
						openPerspective(perspectiveId, activeWorkbenchWindow);
					}
				} catch (ExecutionException e) {
					found[0] = e;
				}
			});
			if (found[0] instanceof ExecutionException) {
				throw ((ExecutionException) found[0]);
