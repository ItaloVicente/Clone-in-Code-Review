		while (true) {
			final FileSnapshot snapshot = FileSnapshot.save(packedRefsFile);
			final BufferedReader br;
			final MessageDigest digest = Constants.newMessageDigest();
			try {
				br = new BufferedReader(new InputStreamReader(
						new DigestInputStream(new FileInputStream(packedRefsFile)
								digest)
			} catch (FileNotFoundException noPackedRefs) {
				return PackedRefList.NO_PACKED_REFS;
			}
			try {
				return new PackedRefList(parsePackedRefs(br)
						ObjectId.fromRaw(digest.digest()));
			} catch (IOException e) {
				if (isStaleFileHandle(e)) {
					continue;
				}
				throw e;
			} finally {
				br.close();
			}
