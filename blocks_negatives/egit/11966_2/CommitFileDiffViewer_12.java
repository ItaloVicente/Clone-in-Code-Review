				if (!CompareUtils.canDirectlyOpenInCompare(file))
					GitModelSynchronize.synchronizeModelWithWorkspace(file,
							getRepository(), commit.getName());
				else
					CompareUtils.compareWorkspaceWithRef(getRepository(), file,
							commit.getName(), null);
