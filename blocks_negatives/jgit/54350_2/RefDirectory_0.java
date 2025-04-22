		final FileSnapshot snapshot = FileSnapshot.save(packedRefsFile);
		final BufferedReader br;
		final MessageDigest digest = Constants.newMessageDigest();
		try {
			br = new BufferedReader(new InputStreamReader(
					new DigestInputStream(new FileInputStream(packedRefsFile),
							digest), CHARSET));
		} catch (FileNotFoundException noPackedRefs) {
			return PackedRefList.NO_PACKED_REFS;
		}
		try {
			return new PackedRefList(parsePackedRefs(br), snapshot,
					ObjectId.fromRaw(digest.digest()));
		} finally {
			br.close();
