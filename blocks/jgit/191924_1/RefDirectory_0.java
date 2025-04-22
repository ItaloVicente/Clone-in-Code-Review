		try {
			PackedRefList result = FileUtils.readWithRetries(packedRefsFile
					f -> {
						final FileSnapshot snapshot = FileSnapshot.save(f);
						final MessageDigest digest = Constants
								.newMessageDigest();
						try (BufferedReader br = new BufferedReader(
								new InputStreamReader(new DigestInputStream(
										new FileInputStream(packedRefsFile)
										digest)
							return new PackedRefList(parsePackedRefs(br)
									snapshot
									ObjectId.fromRaw(digest.digest()));
