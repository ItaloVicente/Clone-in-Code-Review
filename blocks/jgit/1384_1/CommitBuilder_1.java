	public byte[] format() throws UnsupportedEncodingException {
		return format(new ObjectInserter.Formatter());
	}

	public byte[] format(ObjectInserter oi) throws UnsupportedEncodingException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os
		try {
			os.write(htree);
			os.write(' ');
			getTreeId().copyTo(os);
			os.write('\n');

			for (ObjectId p : getParentIds()) {
				os.write(hparent);
				os.write(' ');
				p.copyTo(os);
				os.write('\n');
			}

			os.write(hauthor);
			os.write(' ');
			w.write(getAuthor().toExternalString());
			w.flush();
			os.write('\n');

			os.write(hcommitter);
			os.write(' ');
			w.write(getCommitter().toExternalString());
			w.flush();
			os.write('\n');

			if (getEncoding() != Constants.CHARSET) {
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(getEncoding().name()));
				os.write('\n');
			}

			os.write('\n');

			if (getMessage() != null) {
				w.write(getMessage());
				w.flush();
			}
		} catch (IOException err) {
			throw new RuntimeException(err);
		}

		byte[] content = os.toByteArray();
		setCommitId(oi.idFor(Constants.OBJ_COMMIT
		return content;
	}

