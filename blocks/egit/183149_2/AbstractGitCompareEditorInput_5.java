		if (child != root) {
			if (!repository.isBare()) {
				path = Path
						.fromOSString(
								repository.getWorkTree().getAbsolutePath())
						.append(path);
			}
			path = path.removeLastSegments(1);
