	public void run() {
		int nullKey = 0;
		CacheLoader cl = new CacheLoader(client);
		for(int i = 0; i<N; i++) {
			String k = "multi." + i;
			keys.add(k);
			cl.push(k, value);
		}
