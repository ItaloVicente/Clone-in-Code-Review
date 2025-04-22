
		File file = new File(db.getWorkTree()
		assertFalse("\"all
		file = new File(db.getWorkTree()
		assertTrue("\"all
		file = new File(db.getWorkTree()
		assertFalse("\"all
		file = new File(db.getWorkTree()
		assertTrue("\"all
	}

	private void resolveRelativeUris() {
		defaultUri = defaultDb.getDirectory().toURI().toString();
		notDefaultUri = notDefaultDb.getDirectory().toURI().toString();
		groupAUri = groupADb.getDirectory().toURI().toString();
		groupBUri = groupBDb.getDirectory().toURI().toString();
		int start = 0;
		while (start <= defaultUri.length()) {
			int newStart = defaultUri.indexOf('/'
			String prefix = defaultUri.substring(0
			if (!notDefaultUri.startsWith(prefix) ||
					!groupAUri.startsWith(prefix) ||
					!groupBUri.startsWith(prefix)) {
				start++;
				rootUri = defaultUri.substring(0
				defaultUri = defaultUri.substring(start);
				notDefaultUri = notDefaultUri.substring(start);
				groupAUri = groupAUri.substring(start);
				groupBUri = groupBUri.substring(start);
				return;
			}
			start = newStart;
		}
