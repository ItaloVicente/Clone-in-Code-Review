		final boolean shellChanged = newActiveShell != lastActiveShell;
		final boolean windowChanged = newActiveWorkbenchWindowShell != lastActiveWorkbenchWindowShell;
		final boolean coolbarChanged = newCoolbarVisibility != lastCoolbarVisibility;
		final boolean statusLineChanged = newStatusLineVis != lastStatusLineVisibility;

		final boolean perspectiveBarChanged = newPerspectiveBarVisibility != lastPerspectiveBarVisibility;
		final boolean perspectiveIdChanged = !Util.equals(
				lastPerspectiveId, perspectiveId);
		if (shellChanged && windowChanged) {
			final Map sourceValuesByName1 = new HashMap(5);
			sourceValuesByName1.put(ISources.ACTIVE_SHELL_NAME,
					newActiveShell);
			sourceValuesByName1.put(ISources.ACTIVE_WORKBENCH_WINDOW_NAME,
					newActiveWorkbenchWindow);
			sourceValuesByName1.put(
					ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
					newActiveWorkbenchWindowShell);
			int sourceFlags1 = ISources.ACTIVE_SHELL
					| ISources.ACTIVE_WORKBENCH_WINDOW;

			if (coolbarChanged) {
				sourceValuesByName1
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
								newCoolbarVisibility);
				sourceFlags1 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (statusLineChanged) {
				sourceValuesByName1.put(STATUS_LINE_VIS, newStatusLineVis);
				sourceFlags1 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (perspectiveBarChanged) {
				sourceValuesByName1
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
								newPerspectiveBarVisibility);
				sourceFlags1 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
			if (perspectiveIdChanged) {
				sourceValuesByName1
						.put(
								ISources.ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME,
								perspectiveId);
				sourceFlags1 |= ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE;
			}
