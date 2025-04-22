		if (!find(commit, resultPath))
			return this;

		Candidate c = new Candidate(getRepository(), commit, resultPath);
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0, 0, c.sourceText.size());
		remaining = c.sourceText.size();
		push(c);
		return this;
