	public byte[] format() {
		return format(new ObjectInserter.Formatter());
	}

	public byte[] format(ObjectInserter oi) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os
		try {
			w.write("object ");
			getObjectId().copyTo(w);
			w.write('\n');

			w.write("type ");
			w.write(Constants.typeString(getObjectType()));
			w.write("\n");

			w.write("tag ");
			w.write(getTag());
			w.write("\n");

			if (getTagger() != null) {
				w.write("tagger ");
				w.write(getTagger().toExternalString());
				w.write('\n');
			}

			w.write('\n');
			if (getMessage() != null)
				w.write(getMessage());
			w.close();
		} catch (IOException err) {
			throw new RuntimeException(err);
		}

		byte[] content = os.toByteArray();
		setTagId(oi.idFor(Constants.OBJ_TAG
		return content;
	}

