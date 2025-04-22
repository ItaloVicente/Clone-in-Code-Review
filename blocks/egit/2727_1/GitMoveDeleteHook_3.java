				RepositoryMapping mapping = RepositoryMapping
						.getMapping(source);
				IPath gitDir = mapping.getGitDirAbsolutePath();
				try {
					RepositoryProvider.unmap(source);
				} catch (TeamException e) {
					tree.failed(new Status(IStatus.ERROR, Activator
							.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, e));
					return true; // Do not let Eclipse complete the operation
				}

				monitor.worked(100);
				if (!moveIndexContent(dPath, srcm, sPath)) {
					tree.failed(new Status(IStatus.ERROR, Activator
							.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, null));
					return true;
				}
				tree.standardMoveProject(source, description, updateFlags,
						monitor);

				IProject destination = source.getWorkspace().getRoot().getProject(description.getName());
				GitProjectData projectData = new GitProjectData(destination);
				RepositoryMapping repositoryMapping = new RepositoryMapping(
						destination, gitDir.toFile());
				projectData.setRepositoryMappings(Arrays
						.asList(repositoryMapping));
				projectData.store();
				GitProjectData.add(destination, projectData);
				RepositoryProvider.map(destination, GitProvider.class.getName());
				destination.refreshLocal(IResource.DEPTH_INFINITE,
						new SubProgressMonitor(monitor, 50));
