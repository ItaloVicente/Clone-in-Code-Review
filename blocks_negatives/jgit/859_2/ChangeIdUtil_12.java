		ObjectWriter w = new ObjectWriter(null);
		byte[] bytes = b.toString().getBytes(Constants.CHARACTER_ENCODING);
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		ObjectId sha1 = w.computeObjectSha1(Constants.OBJ_COMMIT, bytes.length,
				is);
		return sha1;
