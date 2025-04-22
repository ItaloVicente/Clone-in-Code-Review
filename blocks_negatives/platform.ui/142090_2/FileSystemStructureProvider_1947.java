        return ((File) element).isDirectory();
    }

    /**
     * Clears the visited dir information
     */
    public void clearVisitedDirs() {
    	if(visitedDirs!=null)
    		visitedDirs.clear();
    }
