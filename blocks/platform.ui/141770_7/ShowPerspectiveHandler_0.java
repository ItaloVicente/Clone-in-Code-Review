				Object[] found = new Object[1];
				BusyIndicator.showWhile(null, () -> {
					try {
						openPerspective(perspectiveId, activeWorkbenchWindow);
					} catch (ExecutionException e) {
						found[0] = e;
					}
				});
				if (found[0] instanceof ExecutionException) {
					throw ((ExecutionException) found[0]);
				}
