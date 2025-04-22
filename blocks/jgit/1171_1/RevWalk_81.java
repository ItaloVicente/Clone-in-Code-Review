	public RevObject peel(RevObject obj) throws MissingObjectException
			IOException {
		while (obj instanceof RevTag) {
			parseHeaders(obj);
			obj = ((RevTag) obj).getObject();
		}
		parseHeaders(obj);
		return obj;
	}

