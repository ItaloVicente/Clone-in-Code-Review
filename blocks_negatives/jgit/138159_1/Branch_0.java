	protected void run() throws Exception {
		if (delete != null || deleteForce != null) {
			if (delete != null) {
				delete(delete, false);
			}
			if (deleteForce != null) {
				delete(deleteForce, true);
