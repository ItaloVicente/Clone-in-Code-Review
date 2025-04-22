	public void gotData(String key, int flags, byte[] data) {
		Collection<GetOperation.Callback> cbs=callbacks.get(key);
		assert cbs != null : "No callbacks for key " + key;
		for(GetOperation.Callback c : cbs) {
			c.gotData(key, flags, data);
		}
	}
