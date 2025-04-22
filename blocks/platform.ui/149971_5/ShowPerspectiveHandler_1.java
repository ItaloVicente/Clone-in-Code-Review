			ExecutionException[] exception = new ExecutionException[1];
			BusyIndicator.showWhile(null, () -> {
				try {
					if (IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode && persp != null) {
						openNewWindowPerspective(perspectiveId, activeWorkbenchWindow);
					} else {
						openPerspective(perspectiveId, activeWorkbenchWindow);
					}
				} catch (ExecutionException e) {
					exception[0] = e;
				}
			});
			if (exception[0] != null) {
				throw exception[0];
