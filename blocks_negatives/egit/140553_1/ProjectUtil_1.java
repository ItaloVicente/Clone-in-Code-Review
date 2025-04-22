		for (int i = 0; i < contents.length; i++) {
			if (!contents[i].isDirectory())
				continue;
			if (contents[i].getName().equals(METADATA_FOLDER))
				continue;
			String path = contents[i].getAbsolutePath();
			if (!directoriesVisited.add(path))
				continue;
			findProjectFiles(files, contents[i], searchNested,
					directoriesVisited, pm);
