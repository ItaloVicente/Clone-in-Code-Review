			} else {
				if (emptyWorkingSet) {
				    emptyWorkingSet = false;
					workingSetFilter.setWorkingSet(workingSet);
				}
			}
			getViewer().refresh();
	    }
	};
