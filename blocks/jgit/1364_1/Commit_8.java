	public void setEncoding(Charset enc) {
		encoding = enc;
	}

	public Charset getEncoding() {
		return encoding;
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Commit");
		if (commitId != null)
			r.append("[" + commitId.name() + "]");
		r.append("={\n");

		r.append("tree ");
		r.append(treeId != null ? treeId.name() : "NOT_SET");
		r.append("\n");

		for (ObjectId p : parentIds) {
			r.append("parent ");
			r.append(p.name());
			r.append("\n");
		}

		r.append("author ");
		r.append(author != null ? author.toString() : "NOT_SET");
		r.append("\n");

		r.append("committer ");
		r.append(committer != null ? committer.toString() : "NOT_SET");
		r.append("\n");

		if (encoding != null && encoding != Constants.CHARSET) {
			r.append("encoding ");
			r.append(encoding.name());
			r.append("\n");
		}

		r.append("\n");
		r.append(message != null ? message : "");
		r.append("}");
		return r.toString();
