
	@Override
	public String toString() {
		byte[] raw = toByteArray();

		CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(raw);

		StringBuilder r = new StringBuilder();
		r.append("Tree={");
		if (!p.eof()) {
			r.append('\n');
			try {
				new ObjectChecker().checkTree(raw);
			} catch (CorruptObjectException error) {
				r.append("*** ERROR: ").append(error.getMessage()).append("\n");
				r.append('\n');
			}
		}
		while (!p.eof()) {
			final FileMode mode = p.getEntryFileMode();
			r.append(mode);
			r.append(' ');
			r.append(Constants.typeString(mode.getObjectType()));
			r.append(' ');
			r.append(p.getEntryObjectId().name());
			r.append(' ');
			r.append(p.getEntryPathString());
			r.append('\n');
			p.next();
		}
		r.append("}");
		return r.toString();
	}
