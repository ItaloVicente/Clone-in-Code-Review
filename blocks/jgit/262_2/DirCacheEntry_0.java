		if (!isValidPath(newPath))
			throw new IllegalArgumentException("Invalid path: "
					+ toString(newPath));
		if (stage < 0 || 3 < stage)
			throw new IllegalArgumentException("Invalid stage " + stage
					+ " for path " + toString(newPath));

