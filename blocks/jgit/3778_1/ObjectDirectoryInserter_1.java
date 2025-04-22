		if (len <= buffer().length) {
			byte[] buf = buffer();
			IO.readFully(is
			return insert(type

		} else {
			MessageDigest md = digest();
			File tmp = toTemp(md
			ObjectId id = ObjectId.fromRaw(md.digest());
			return insertOneObject(tmp
		}
	}
