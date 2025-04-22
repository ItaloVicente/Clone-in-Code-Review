				if (conflicting) {
					GitFileRevision revision = GitFileRevision.inIndex(
							repository, gitPath, DirCacheEntry.STAGE_3);
					String encoding = CompareCoreUtils.getResourceEncoding(
							repository, gitPath);
					right = new FileRevisionTypedElement(revision, encoding);
				} else
