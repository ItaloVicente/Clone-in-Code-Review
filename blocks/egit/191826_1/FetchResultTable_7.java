			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				if (loader != null && oldInput != null) {
					loader.cancel(oldInput);
				}
				currentInput = newInput;
				super.inputChanged(viewer, oldInput, newInput);
			}
