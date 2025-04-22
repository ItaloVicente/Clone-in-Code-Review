		if (!entry.isMerged()) {
			IFileRevision revision = GitFileRevision.inIndex(repository,
					gitPath, DirCacheEntry.STAGE_2);
			return new FileRevisionTypedElement(revision, encoding);
		}

