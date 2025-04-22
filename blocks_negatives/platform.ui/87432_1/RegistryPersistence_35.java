		registryChangeListener = new IRegistryChangeListener() {
			@Override
			public final void registryChanged(final IRegistryChangeEvent event) {
				if (isChangeImportant(event)) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public final void run() {
							read();
						}
					});
				}
