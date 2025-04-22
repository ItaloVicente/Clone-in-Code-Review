							result.put(container, Boolean.FALSE);
						}
					} else if (isFile) {
						String lastPart = filePath.lastSegment();
						while (containerPath != null
								&& workTree.isPrefixOf(containerPath)) {
							IContainer container = ResourceUtil
									.getContainerForLocation(containerPath,
											false);
							if (container == null) {
								lastPart = containerPath.lastSegment();
								containerPath = containerPath
										.removeLastSegments(1);
								isFile = false;
								continue;
							}
							if (container.getType() == IResource.ROOT) {
								containerPath = containerPath.append(lastPart);
								fullRefreshes.add(containerPath);
								handled.put(containerPath, null);
							} else if (isFile) {
								IFile file = container
										.getFile(new Path(lastPart));
								handled.put(containerPath, file);
								result.put(file, Boolean.FALSE);
							} else {
								container = container
										.getFolder(new Path(lastPart));
								containerPath = containerPath.append(lastPart);
								fullRefreshes.add(containerPath);
								handled.put(containerPath, null);
								result.put(container, Boolean.TRUE);
