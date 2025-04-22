	private int umask() {
		int u = umask;
		if (u == -1) {
			u = readUmask();
			umask = u;
		}
		return u;
