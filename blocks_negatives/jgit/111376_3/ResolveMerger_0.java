		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] {});

		ObjectLoader loader = reader.open(id, OBJ_BLOB);
		int threshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
		return RawText.load(loader, threshold);
