
				checkoutTree();
				pm.worked(1);

				updateHeadRef();
				pm.worked(1);

				ProjectUtil.refreshValidProjects(validProjects, new SubProgressMonitor(
						pm, 1));
