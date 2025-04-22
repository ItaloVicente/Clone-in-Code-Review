		if (predefined != null) {
			for (final RefSpec pre : predefined) {
				if (!specs.contains(pre)) {
					enable = true;
					break;
				}
