			public void dispose() {
				if (loader != null && currentInput != null) {
					loader.cancel(currentInput);
				}
				currentInput = null;
				loader = null;
				super.dispose();
