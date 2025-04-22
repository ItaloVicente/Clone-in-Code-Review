			MoveResult result = null;
			if (org.eclipse.egit.core.Activator.autoStageMoves()) {
				result = moveIndexContent(dPath, srcm, sPath);
			} else {
				result = checkUnmergedPaths(srcm, sPath);
			}
