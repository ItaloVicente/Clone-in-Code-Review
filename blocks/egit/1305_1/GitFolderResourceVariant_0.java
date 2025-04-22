				if (!newObjectId.equals(zeroId()))
					if (tw.isSubtree())
						result.add(new GitFolderResourceVariant(repo,
								newObjectId, path));
					else
						result.add(new GitBlobResourceVariant(repo,
								newObjectId, path));
