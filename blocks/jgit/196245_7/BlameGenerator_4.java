	public BlameGenerator push(RevCommit blameCommit) throws IOException {
		if (!find(blameCommit
			return this;
		}

		Candidate c = new Candidate(getRepository()
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0
		remaining = c.sourceText.size();

		push(c);
		return this;
	}

