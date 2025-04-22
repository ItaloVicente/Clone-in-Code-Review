	private String describe(ObjectId c1
			int abbrev) throws GitAPIException
		return git.describe().setTarget(c1).setTags(describeUseAllTags)
				.setLong(longDesc).setAlways(always).setAbbrev(abbrev).call();
	}

