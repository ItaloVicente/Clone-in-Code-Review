		MinMaxAddonUtil.restoreStacksOfMinMaxChildrenArea(this, element, maximizeTag);
	}

	void executeWithIgnoredTagChanges(Runnable runnable) {
		ignoreTagChanges = true;
		try {
			runnable.run();
		} finally {
			ignoreTagChanges = false;
		}
