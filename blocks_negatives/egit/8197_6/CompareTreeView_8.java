				} else if (compareVersionIterator != null
						&& baseVersionIterator == null) {
					monitor.setTaskName(compareVersionIterator
							.getEntryPathString());
					IPath currentPath = new Path(compareVersionIterator
							.getEntryPathString());
					deletedPaths.add(currentPath);
					List<PathNodeAdapter> children = compareVersionPathsWithChildren
							.get(currentPath.removeLastSegments(1));
					if (children == null) {
						children = new ArrayList<PathNodeAdapter>(1);
						compareVersionPathsWithChildren.put(currentPath
								.removeLastSegments(1), children);
