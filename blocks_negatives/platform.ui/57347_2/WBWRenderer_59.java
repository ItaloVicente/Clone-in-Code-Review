		localContext.set(IShellProvider.class, new IShellProvider() {
			@Override
			public Shell getShell() {
				return wbwShell;
			}
		});
