	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Tag");
		if (tagId != null)
			r.append("[" + tagId.name() + "]");
		r.append("={\n");

		r.append("object ");
		r.append(object != null ? object.name() : "NOT_SET");
		r.append("\n");

		r.append("type ");
		r.append(object != null ? Constants.typeString(type) : "NOT_SET");
		r.append("\n");

		r.append("tag ");
		r.append(tag != null ? tag : "NOT_SET");
		r.append("\n");

		if (tagger != null) {
			r.append("tagger ");
			r.append(tagger);
			r.append("\n");
		}
