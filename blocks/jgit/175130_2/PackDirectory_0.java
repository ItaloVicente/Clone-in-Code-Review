			final PackFileName name;
			try {
				name = new PackFileName(directory
			} catch (IllegalArgumentException e) {
				continue;
			}
			if (!INDEX.equals(name.getPackExt())) {
