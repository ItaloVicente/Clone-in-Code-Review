	public boolean setVBucket(String k, short vb) {
		if (k.equals(key)) {
			vbucket = vb;
			return true;
		}
		return false;
	}
