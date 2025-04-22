        ignoreFilterChangeEvents = true;
        try {
        	workingSetActionGroup.setWorkingSet(workingSet);
        } finally {
        	ignoreFilterChangeEvents = false;
       	}
