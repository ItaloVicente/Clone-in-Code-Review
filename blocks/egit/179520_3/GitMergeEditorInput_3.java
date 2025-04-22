				} else if (conflicting) {
					GitFileRevision revision = GitFileRevision.inIndex(
							repository, gitPath, DirCacheEntry.STAGE_1);
					if (encoding == null) {
						encoding = CompareCoreUtils
								.getResourceEncoding(repository, gitPath);
					}
					ancestor = new FileRevisionTypedElement(revision, encoding);
