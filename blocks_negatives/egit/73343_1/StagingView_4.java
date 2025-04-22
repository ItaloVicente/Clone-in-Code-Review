			CheckoutCommand checkoutCommand = new Git(repository)
					.checkout();
			if (headRevision) {
				checkoutCommand.setStartPoint(Constants.HEAD);
			}
			for (String path : files) {
				checkoutCommand.addPath(path);
			}
			try {
