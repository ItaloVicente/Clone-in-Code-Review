			final boolean shellChanged = newActiveShell != lastActiveShell;
			final boolean windowChanged = newActiveWorkbenchWindowShell != lastActiveWorkbenchWindowShell;
			final boolean coolbarChanged = newCoolbarVisibility != lastCoolbarVisibility;
			final boolean statusLineChanged = newStatusLineVis != lastStatusLineVisibility;

			final boolean perspectiveBarChanged = newPerspectiveBarVisibility != lastPerspectiveBarVisibility;
			final boolean perspectiveIdChanged = !Util.equals(
					lastPerspectiveId, perspectiveId);
			if (shellChanged && windowChanged) {
				final Map sourceValuesByName = new HashMap(5);
				sourceValuesByName.put(ISources.ACTIVE_SHELL_NAME,
						newActiveShell);
				sourceValuesByName.put(ISources.ACTIVE_WORKBENCH_WINDOW_NAME,
						newActiveWorkbenchWindow);
				sourceValuesByName.put(
						ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
						newActiveWorkbenchWindowShell);
				int sourceFlags = ISources.ACTIVE_SHELL
						| ISources.ACTIVE_WORKBENCH_WINDOW;

				if (coolbarChanged) {
					sourceValuesByName
							.put(
									ISources.ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
									newCoolbarVisibility);
					sourceFlags |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
				}
				if (statusLineChanged) {
					sourceValuesByName.put(STATUS_LINE_VIS, newStatusLineVis);
					sourceFlags |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
				}
				if (perspectiveBarChanged) {
					sourceValuesByName
							.put(
									ISources.ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
									newPerspectiveBarVisibility);
					sourceFlags |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
				}
				if (perspectiveIdChanged) {
					sourceValuesByName
							.put(
									ISources.ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME,
									perspectiveId);
					sourceFlags |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
				}
