
	private void findSymrefs(final RefAdvertiser adv) throws IOException {
		Ref head = db.getRef(Constants.HEAD);
		if (head != null && head.isSymbolic()) {
			adv.addSymref(Constants.HEAD
		}
	}
