		shell.addDisposeListener(e -> {
			Shell shell1 = (Shell) e.widget;
			if (disposeME != null) {
				disposeME.getTags().remove(ShellMinimizedTag);
				disposeME.getTags().remove(ShellMaximizedTag);
				if (shell1.getMinimized())
					disposeME.getTags().add(ShellMinimizedTag);
				if (shell1.getMaximized())
					disposeME.getTags().add(ShellMaximizedTag);
