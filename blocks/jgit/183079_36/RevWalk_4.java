	public void parseHeadersInGraph(RevCommit c)
			throws IOException
		if ((c.flags & PARSED) == 0) {
			c.parseHeadersInGraph(this);
		}
	}

