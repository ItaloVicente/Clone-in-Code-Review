			try (Git git = new Git(repository)) {
				CheckoutCommand checkoutCommand = git.checkout();
				if (headRevision) {
					checkoutCommand.setStartPoint(Constants.HEAD);
				}
				for (String path : files) {
					checkoutCommand.addPath(path);
				}
