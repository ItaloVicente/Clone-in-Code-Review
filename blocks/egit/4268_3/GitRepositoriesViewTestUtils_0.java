	public static RepositoriesViewLabelProvider createLabelProvider() {
		final AtomicReference<RepositoriesViewLabelProvider> providerRef = new AtomicReference<RepositoriesViewLabelProvider>();
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				providerRef.set(new RepositoriesViewLabelProvider());
			}

		});
		return providerRef.get();
	}

