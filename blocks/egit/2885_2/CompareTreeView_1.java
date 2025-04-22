				boolean isFile = ((IResource) child).getType() == IResource.FILE;

				if (isFile && !compareVersionMap.containsKey(path)
						&& !addedPaths.contains(path)) {
					rebuildArray = true;
					continue;
				}
