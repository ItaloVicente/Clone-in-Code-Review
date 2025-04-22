					deletedPaths.add(currentPath);
					List<PathNodeAdapter> children = compareVersionPathsWithChildren
							.get(currentPath.removeLastSegments(1));
					if (children == null) {
						children = new ArrayList<PathNodeAdapter>(1);
						compareVersionPathsWithChildren.put(currentPath
								.removeLastSegments(1), children);
					}
					children.add(new PathNodeAdapter(new PathNode(currentPath,
							Type.FILE_DELETED)));
