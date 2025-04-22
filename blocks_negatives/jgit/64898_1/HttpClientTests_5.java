		Transport t = Transport.open(smartAuthNoneURI);
		try {
			FetchConnection c = t.openFetch();
			try {
				Ref head = c.getRef(Constants.HEAD);
				assertNotNull(head);
			} finally {
				c.close();
			}
		} finally {
			t.close();
