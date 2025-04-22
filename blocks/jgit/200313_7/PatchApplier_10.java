			if (fh.getChangeType() != ChangeType.DELETE)
				modifiedPaths.add(fh.getNewPath());
			if (fh.getChangeType() != ChangeType.COPY
					&& fh.getChangeType() != ChangeType.ADD)
				modifiedPaths.add(fh.getOldPath());
		}
