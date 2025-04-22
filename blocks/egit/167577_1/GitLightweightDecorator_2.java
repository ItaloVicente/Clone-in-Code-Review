			notifyListeners();
		}

		private void notifyListeners() {
			for (Runnable listener : listeners) {
				SafeRunnable.run(listener::run);
			}
		}

		public void addListener(Runnable runnable) {
			listeners.addIfAbsent(runnable);
		}

		public void removeListener(Runnable runnable) {
			listeners.remove(runnable);
		}

		public void dispose() {
			listeners.clear();
			PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
					.removePropertyChangeListener(themeListener);
