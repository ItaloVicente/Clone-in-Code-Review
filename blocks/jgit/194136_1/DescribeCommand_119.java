	public DescribeCommand setAbbrev(int abbrev) {
		if (abbrev == 0) {
			this.abbrev = 0;
		} else {
			this.abbrev = AbbrevConfig.capAbbrev(abbrev);
		}
		return this;
	}

