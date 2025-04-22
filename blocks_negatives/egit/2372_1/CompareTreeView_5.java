
					while (currentPath.segmentCount() > 0) {
						currentPath = currentPath.removeLastSegments(1);
						if (!rightPathsWithChildren.add(currentPath))
							break;
					}
