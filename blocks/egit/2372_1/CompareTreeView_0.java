					List<PathNodeAdapter> children = rightPathsWithChildren
							.get(currentPath.removeLastSegments(1));
					if (children == null) {
						children = new ArrayList<PathNodeAdapter>(1);
						rightPathsWithChildren.put(currentPath
								.removeLastSegments(1), children);
					}
					children.add(new PathNodeAdapter(new PathNode(currentPath,
							Type.FILE_DELETED)));
