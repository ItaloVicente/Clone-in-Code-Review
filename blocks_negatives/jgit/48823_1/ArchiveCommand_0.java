			try (final RevWalk rw = new RevWalk(walk.getObjectReader())) {
				final MutableObjectId idBuf = new MutableObjectId();
				final ObjectReader reader = walk.getObjectReader();

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
						fmt.putEntry(outa, name + "/", mode, null); //$NON-NLS-1$
						continue;
					}
					walk.getObjectId(idBuf, 0);
					fmt.putEntry(outa, name, mode, reader.open(idBuf));
