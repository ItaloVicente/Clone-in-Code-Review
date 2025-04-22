		if (exists) {
			try {
				return new FileInputStream(path.toFile());
			} catch (FileNotFoundException e) {
				Activator.error(e.getMessage(), e);
			}
