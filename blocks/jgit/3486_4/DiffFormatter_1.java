			formatIndexLine(o
		}
	}

	protected void formatIndexLine(OutputStream o
			throws IOException {
				+ format(ent.getNewId())));
		if (ent.getOldMode().equals(ent.getNewMode())) {
			o.write(' ');
			ent.getNewMode().copyTo(o);
