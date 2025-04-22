        private class KeyInfo implements Comparable<KeyInfo> {
	    String name;
	    long lastModifiedSecs;
	    public KeyInfo(String aname
		name = aname;
		lastModifiedSecs = lsecs;
	    }

	    @Override
	    public int compareTo(KeyInfo other) {
		if (this.lastModifiedSecs < other.lastModifiedSecs) {
		    return 1;
		} else if (this.lastModifiedSecs == other.lastModifiedSecs) {
		    return 0;
		} else {
		    return -1;
		}
	    }
	}

