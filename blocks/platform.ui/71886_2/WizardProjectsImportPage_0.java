		for (File dir : directories) {
			if (!dir.getName().equals(METADATA_FOLDER)) {
				try {
					String canonicalPath = dir.getCanonicalPath();
					if (!directoriesVisited.add(canonicalPath)) {
						continue;
