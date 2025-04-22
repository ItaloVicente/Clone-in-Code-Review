			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return FileSystemSupportRegistry.getInstance()
						.getConfigurations();
			}

			@Override
			public void inputChanged(org.eclipse.jface.viewers.Viewer viewer,
					Object oldInput, Object newInput) {
			}

		});

		fileSystems.setInput(this);
