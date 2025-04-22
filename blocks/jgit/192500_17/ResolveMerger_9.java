		if (detectRenames) {
			boolean success = processRenames(baseTree
			if (!success) {
				cleanUp();
				return false;
			}
		}

