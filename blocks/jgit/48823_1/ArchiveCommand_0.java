			final MutableObjectId idBuf = new MutableObjectId();

			walk.reset(rw.parseTree(tree));
			if (!paths.isEmpty())
				walk.setFilter(PathFilterGroup.createFromStrings(paths));

			while (walk.next()) {
				final String name = pfx + walk.getPathString();
				FileMode mode = walk.getFileMode(0);

				if (walk.isSubtree())
					walk.enterSubtree();

				if (mode == FileMode.GITLINK)
					mode = FileMode.TREE;

				if (mode == FileMode.TREE) {
					fmt.putEntry(outa
					continue;
