		final Tag t = new Tag(db);
		t.setType(Constants.typeString(dst.getType()));
		t.setObjId(dst.toObjectId());
		t.setTag(name);
		t.setTagger(new PersonIdent(committer, new Date(nowTick)));
		t.setMessage("");
		return (RevTag) rw.lookupAny(ow.writeTag(t), Constants.OBJ_TAG);
	}

	protected <T extends RevObject> T parse(final T t) throws Exception {
		rw.parseBody(t);
		return t;
