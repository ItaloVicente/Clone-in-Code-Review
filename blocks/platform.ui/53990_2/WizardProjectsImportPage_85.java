					if (!collectProjectFilesFromProvider(files, child1, 0,
							monitor)) {
						return;
					}
					Iterator filesIterator1 = files.iterator();
					selectedProjects = new ProjectRecord[files.size()];
					int index1 = 0;
					monitor.worked(50);
