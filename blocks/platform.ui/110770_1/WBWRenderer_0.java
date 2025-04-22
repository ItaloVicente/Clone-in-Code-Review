		 try {
			if (shellME.getTags().contains(ShellMaximizedTag)) {
				shell.setMaximized(true);
			} else if (shellME.getTags().contains(ShellMinimizedTag)) {
				shell.setMinimized(true);
			}
