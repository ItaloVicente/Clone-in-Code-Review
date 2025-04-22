		IgnoreNode load() throws IOException {
			IgnoreNode r;
			if (entry != null) {
				r = super.load();
				if (r == null)
					r = new IgnoreNode();
			} else {
				r = new IgnoreNode();
			}

