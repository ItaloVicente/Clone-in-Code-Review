				IFile file = (IFile) input;
				if (CompareUtils.canDirectlyOpenInCompare(file))
					CompareUtils.compareWorkspaceWithRef(repository, file,
							commit.getId().getName(), workBenchPage);
				else
					GitModelSynchronize.synchronizeModelWithWorkspace(file,
							repository, commit.getName());
			} else
				CompareUtils.compareLocalWithRef(repository, (File) input,
						commit.getId().getName(), workBenchPage);
