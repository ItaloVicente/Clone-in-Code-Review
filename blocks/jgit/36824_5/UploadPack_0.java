
	private void findSymrefs(
			final RefAdvertiser adv
		Ref head = refs.get(Constants.HEAD);
		if (head != null && head.isSymbolic()) {
			adv.addSymref(Constants.HEAD
		}
	}
