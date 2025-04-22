		try {
			PackedRefList result = FileUtils.readWithRetries(packedRefsFile
					f -> {
						FileSnapshot snapshot = FileSnapshot.save(f);
						MessageDigest digest = Constants.newMessageDigest();
						try (BufferedReader br = new BufferedReader(
								new InputStreamReader(
										new DigestInputStream(
												new FileInputStream(f)
										UTF_8))) {
							return new PackedRefList(parsePackedRefs(br)
									snapshot
									ObjectId.fromRaw(digest.digest()));
