	protected void run() throws Exception {
		if (delete != null || deleteForce != null) {
			if (delete != null) {
				delete(delete, false);
			}
			if (deleteForce != null) {
				delete(deleteForce, true);
			}
		} else {
			if (rename) {
				String src, dst;
				if (otherBranch == null) {
					final Ref head = db.exactRef(Constants.HEAD);
					if (head != null && head.isSymbolic()) {
						src = head.getLeaf().getName();
