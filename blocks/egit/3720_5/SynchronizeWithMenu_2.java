						if (!(selectedResource instanceof IProject)) {
							HashSet<IContainer> containers = new HashSet<IContainer>();
							containers.add((IContainer) selectedResource);
							data.setIncludedPaths(containers);
						}

						GitModelSynchronize.launch(data, new IResource[] { selectedResource });
