			}

			final Map currentState = getCurrentState();
			final Shell newActiveShell = (Shell) currentState
					.get(ISources.ACTIVE_SHELL_NAME);
			final WorkbenchWindow newActiveWorkbenchWindow = (WorkbenchWindow) currentState
					.get(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
			final Shell newActiveWorkbenchWindowShell = (Shell) currentState
					.get(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME);

			final Boolean newCoolbarVisibility = newActiveWorkbenchWindow == null ? lastCoolbarVisibility
					: (newActiveWorkbenchWindow.getCoolBarVisible() ? Boolean.TRUE
							: Boolean.FALSE);
			final Boolean newPerspectiveBarVisibility = newActiveWorkbenchWindow == null ? lastPerspectiveBarVisibility
					: (newActiveWorkbenchWindow.getPerspectiveBarVisible() ? Boolean.TRUE
							: Boolean.FALSE);
			final Boolean newStatusLineVis = newActiveWorkbenchWindow == null ? lastStatusLineVisibility
					: (newActiveWorkbenchWindow.getStatusLineVisible() ? Boolean.TRUE
							: Boolean.FALSE);

			String perspectiveId = lastPerspectiveId;
			if (newActiveWorkbenchWindow != null) {
				IWorkbenchPage activePage = newActiveWorkbenchWindow
						.getActivePage();
				if (activePage != null) {
					IPerspectiveDescriptor perspective = activePage
							.getPerspective();
					if (perspective != null) {
						perspectiveId = perspective.getId();
					}
				}
