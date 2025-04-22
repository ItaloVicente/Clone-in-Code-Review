				MoveResult result = null;
				if (org.eclipse.egit.core.Activator.autoStageMoves()) {
					final String dPath = srcm.getRepoRelativePath(dstf) + "/"; //$NON-NLS-1$
					result = moveIndexContent(dPath, srcm, sPath);
				} else {
					result = checkUnmergedPaths(srcm, sPath);
				}
