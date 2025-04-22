			} else {
				if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) {
					return true;
				}
				if (modeT != 0 && modeT == modeB) {
					return true;
				}
				addDeletion(tw.getPathString(), nonTree(modeO), attributes);
