				IPath gitDir = srcm.getGitDirAbsolutePath();
				if (unmapProject(tree, source))
					return true;

				monitor.worked(100);

