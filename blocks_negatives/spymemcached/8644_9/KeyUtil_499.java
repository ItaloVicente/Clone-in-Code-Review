	/**
	 * Get the keys in byte form for all of the string keys.
	 *
	 * @param keys a collection of keys
	 * @return return a collection of the byte representations of keys
	 */
	public static Collection<byte[]> getKeyBytes(Collection<String> keys) {
		Collection<byte[]> rv=new ArrayList<byte[]>(keys.size());
		for(String s : keys) {
			rv.add(getKeyBytes(s));
		}
		return rv;
	}
