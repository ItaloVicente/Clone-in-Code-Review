		try (Git git = new Git(repository)) {
			CheckoutCommand checkoutCommand = git.checkout();
			if (revision != null) {
				checkoutCommand.setStartPoint(revision);
			}
			if (stage != null) {
				checkoutCommand.setStage(stage.checkoutStage);
			}
			if (paths.isEmpty() || paths.contains("")) { //$NON-NLS-1$
				checkoutCommand.setAllPaths(true);
			} else {
				for (String path : paths) {
					checkoutCommand.addPath(path);
				}
			}
			checkoutCommand.call();
