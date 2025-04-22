	protected void run() {
		try {
			if (delete != null || deleteForce != null) {
				if (delete != null) {
					delete(delete
				}
				if (deleteForce != null) {
					delete(deleteForce
				}
			} else {
				if (rename) {
					String src
					if (otherBranch == null) {
						final Ref head = db.exactRef(Constants.HEAD);
						if (head != null && head.isSymbolic()) {
							src = head.getLeaf().getName();
						} else {
							throw die(CLIText.get().cannotRenameDetachedHEAD);
						}
						dst = branch;
