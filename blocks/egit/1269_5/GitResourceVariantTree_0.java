					return new GitBlobResourceVariant(repo,
							tw.getObjectId(nth), path);
			} else {
				while (tw.next() && !path.equals(tw.getPathString()))
					if (tw.isSubtree())
						tw.enterSubtree();
				return new GitFolderResourceVariant(repo, tw.getObjectId(nth),
						path);
			}
