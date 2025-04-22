		try {
			walk.reset();
			walk.addTree(tree);
			walk.setRecursive(true);
			while (walk.next()) {
				final String name = walk.getPathString();
				final FileMode mode = walk.getFileMode(0);

				if (mode == FileMode.TREE)
					continue;

				walk.getObjectId(idBuf
				fmt.putEntry(name
			}
		} finally {
			reader.release();
			outa.close();
