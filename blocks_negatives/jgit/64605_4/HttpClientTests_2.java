		Transport t = Transport.open(dst, smartAuthNoneURI);
		try {
			FetchConnection c = t.openFetch();
			try {
				head = c.getRef(Constants.HEAD);
			} finally {
				c.close();
			}
		} finally {
			t.close();
