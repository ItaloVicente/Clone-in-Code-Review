				boolean indexDiffAvailable;
				boolean noConflicts;
				if (indexDiff == null) {
					indexDiffAvailable = false;
					noConflicts = true;
				} else {
					indexDiffAvailable = true;
					noConflicts = indexDiff.getConflicting().isEmpty();
				}
