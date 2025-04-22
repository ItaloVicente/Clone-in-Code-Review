			Path fileName = file.getFileName();
			if (fileName != null) {
				String name = fileName.toString();
				if (!attrs.isDirectory() && badName.test(name)) {
					bad.add(name);
				}
