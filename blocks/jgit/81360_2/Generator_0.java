
	public static String describeOutputType(int type) {
		StringBuffer b=new StringBuffer();
		if ((type & SORT_COMMIT_TIME_DESC) != 0) {
			if (b.length()>0)
				b.append('|');
		}
		if ((type & HAS_REWRITE) != 0) {
			if (b.length() > 0)
				b.append('|');
		}
		if ((type & NEEDS_REWRITE) != 0) {
			if (b.length() > 0)
				b.append('|');
		}
		if ((type & SORT_TOPO) != 0) {
			if (b.length() > 0)
				b.append('|');
		}
		if ((type & HAS_UNINTERESTING) != 0) {
			if (b.length() > 0)
				b.append('|');
		}
		return (b.toString());
	}
