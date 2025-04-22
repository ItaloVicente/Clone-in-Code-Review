	public static void dispose() {
		for (int idx = 0; idx < cursors.length; idx++) {
			cursors[idx].dispose();
			cursors[idx] = null;
		}
	}
