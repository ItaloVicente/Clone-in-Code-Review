	/**
	 * Add a key (and return its new opaque value).
	 */
	protected int addKey(String k) {
		Integer rv=rkeys.get(k);
		if(rv == null) {
			rv=generateOpaque();
			keys.put(rv, k);
			bkeys.put(rv, KeyUtil.getKeyBytes(k));
			rkeys.put(k, rv);
			vbmap.put(k, new Short((short)0));
		}
		return rv;
	}
