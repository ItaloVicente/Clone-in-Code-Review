		if (last != null)
			treeWalk = last instanceof RevTree ? enter(last) : treeWalk.next();

		while (!treeWalk.eof()) {
			final FileMode mode = treeWalk.getEntryFileMode();
			switch (mode.getObjectType()) {
			case Constants.OBJ_BLOB: {
				treeWalk.getEntryObjectId(idBuffer);
				final RevBlob o = lookupBlob(idBuffer);
				if ((o.flags & SEEN) != 0)
					break;
				o.flags |= SEEN;
				if (shouldSkipObject(o))
					break;
				last = o;
				return o;
			}
			case Constants.OBJ_TREE: {
				treeWalk.getEntryObjectId(idBuffer);
				final RevTree o = lookupTree(idBuffer);
				if ((o.flags & SEEN) != 0)
					break;
				o.flags |= SEEN;
				if (shouldSkipObject(o))
					break;
				last = o;
				return o;
			}
			default:
				if (FileMode.GITLINK.equals(mode))
					break;
				treeWalk.getEntryObjectId(idBuffer);
				throw new CorruptObjectException(MessageFormat.format(JGitText.get().corruptObjectInvalidMode3
						, mode , idBuffer.name() , treeWalk.getEntryPathString() , currentTree.name()));
			}
