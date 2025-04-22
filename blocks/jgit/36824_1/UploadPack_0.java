
	private void findSymrefs(final RefAdvertiser adv) throws IOException {
		Ref head = db.getRef(Constants.HEAD);
		if (head == null) {
			return;
		}
		adv.addSymref(Constants.HEAD
	}
