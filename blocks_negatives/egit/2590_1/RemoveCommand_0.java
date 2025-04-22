				for (RepositoryNode node : selectedNodes) {
					if (node.getRepository().isBare())
						continue;
					File workDir = node.getRepository().getWorkTree();
					final IPath wdPath = new Path(workDir.getAbsolutePath());
					for (IProject prj : ResourcesPlugin.getWorkspace()
							.getRoot().getProjects()) {
						if (monitor.isCanceled())
							return Status.OK_STATUS;
						if (wdPath.isPrefixOf(prj.getLocation())) {
							projectsToDelete.add(prj);
						}
					}
				}
