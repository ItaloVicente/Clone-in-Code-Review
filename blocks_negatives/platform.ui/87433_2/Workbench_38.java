		return new IShellProvider() {
			@Override
			public Shell getShell() {
				return ProgressManagerUtil.getDefaultParent();
			}
		};
