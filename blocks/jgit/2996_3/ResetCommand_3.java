			if (mode != ResetType.SOFT) {
				if (merging)
					resetMerge();
				else if (cherryPicking)
					resetCherryPick();
			}
