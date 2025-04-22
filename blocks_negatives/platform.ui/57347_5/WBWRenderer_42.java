		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Shell shell = (Shell) e.widget;
				if (disposeME != null) {
					disposeME.getTags().remove(ShellMinimizedTag);
					disposeME.getTags().remove(ShellMaximizedTag);
					if (shell.getMinimized()) {
						disposeME.getTags().add(ShellMinimizedTag);
					}
					if (shell.getMaximized()) {
						disposeME.getTags().add(ShellMaximizedTag);
					}
