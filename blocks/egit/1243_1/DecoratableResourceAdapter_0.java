				} else {
					IPath wdPath = new Path(repository.getWorkTree()
							.getAbsolutePath()).append(workingTreeIterator
							.getEntryPathString());
					IPath resPath = resource.getLocation();
					if (wdPath.equals(resPath)
							&& workingTreeIterator.isEntryIgnored()) {
						ignored = true;
						return false;
					}
