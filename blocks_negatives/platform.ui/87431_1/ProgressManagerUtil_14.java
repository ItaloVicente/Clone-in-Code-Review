		return new IShellProvider() {

			@Override
			public Shell getShell() {
				return getDefaultParent();
			}
		};
