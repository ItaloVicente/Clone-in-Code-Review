		} else if (shellChanged) {
			if (DEBUG) {
				logDebuggingInfo("Active shell changed to " //$NON-NLS-1$
						+ newActiveShell);
			}
			fireSourceChanged(ISources.ACTIVE_SHELL,
					ISources.ACTIVE_SHELL_NAME, newActiveShell);
		} else if (windowChanged) {
			final Map sourceValuesByName2 = new HashMap(4);
			sourceValuesByName2.put(ISources.ACTIVE_WORKBENCH_WINDOW_NAME,
					newActiveWorkbenchWindow);
			sourceValuesByName2.put(
					ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
					newActiveWorkbenchWindowShell);

			int sourceFlags2 = ISources.ACTIVE_SHELL
					| ISources.ACTIVE_WORKBENCH_WINDOW;

			if (coolbarChanged) {
				sourceValuesByName2
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
								newCoolbarVisibility);
				sourceFlags2 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (statusLineChanged) {
				sourceValuesByName2.put(STATUS_LINE_VIS, newStatusLineVis);
				sourceFlags2 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (perspectiveBarChanged) {
				sourceValuesByName2
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
								newPerspectiveBarVisibility);
				sourceFlags2 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (perspectiveIdChanged) {
				sourceValuesByName2
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME,
								perspectiveId);
				sourceFlags2 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
