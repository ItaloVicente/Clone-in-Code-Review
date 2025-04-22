					new BufferedInputStream(data.load()));
		}
	}

	private void initHash(SHA1 hash
		hash.update(Constants.encodedTypeString(Constants.OBJ_BLOB));
		hash.update((byte) ' ');
		hash.update(Constants.encodeASCII(size));
		hash.update((byte) 0);
	}

	private ObjectId hash(File f) throws IOException {
		SHA1 hash = SHA1.newInstance();
		initHash(hash
		try (InputStream input = new FileInputStream(f)) {
			byte[] buf = new byte[8192];
			int n;
			while ((n = input.read(buf)) >= 0) {
				hash.update(buf
			}
		}
		return hash.toObjectId();
	}

	private void checkOid(ObjectId baseId
			String path)
			throws PatchApplyException
		boolean hashOk = false;
		if (id != null) {
			hashOk = baseId.equals(id);
			if (!hashOk && ChangeType.ADD.equals(type)
					&& ObjectId.zeroId().equals(baseId)) {
				hashOk = EMPTY_ID.equals(id);
			}
		} else {
			if (ObjectId.zeroId().equals(baseId)) {
				hashOk = !f.exists() || f.length() == 0;
			} else {
				hashOk = baseId.equals(hash(f));
			}
		}
		if (!hashOk) {
			throw new PatchApplyException(MessageFormat
					.format(JGitText.get().applyBinaryBaseOidWrong
		}
	}

	private void applyBinary(Repository repository
			FileHeader fh
			CheckoutMetadata checkOut)
			throws PatchApplyException
		if (!fh.getOldId().isComplete() || !fh.getNewId().isComplete()) {
			throw new PatchApplyException(MessageFormat
					.format(JGitText.get().applyBinaryOidTooShort
		}
		BinaryHunk hunk = fh.getForwardBinaryHunk();
		int start = RawParseUtils.nextLF(hunk.getBuffer()
				hunk.getStartOffset());
		int length = hunk.getEndOffset() - start;
		SHA1 hash = SHA1.newInstance();
		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try {
			switch (hunk.getType()) {
			case LITERAL_DEFLATED:
				checkOid(fh.getOldId().toObjectId()
						path);
				initHash(hash
				try (OutputStream out = buffer;
						InputStream inflated = new SHA1InputStream(hash
								new InflaterInputStream(
										new BinaryHunkInputStream(
												new ByteArrayInputStream(
														hunk.getBuffer()
														length))))) {
					DirCacheCheckout.getContent(repository
							new StreamLoader(() -> inflated
							null
					if (!fh.getNewId().toObjectId().equals(hash.toObjectId())) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().applyBinaryResultOidWrong
								path));
					}
				}
				Files.copy(buffer.openInputStream()
						StandardCopyOption.REPLACE_EXISTING);
				break;
			case DELTA_DEFLATED:
				byte[] base;
				try (InputStream input = loader.load()) {
					base = IO.readWholeStream(input
				}
				try (BinaryDeltaInputStream input = new BinaryDeltaInputStream(
						base
						new InflaterInputStream(new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer()
										start
					long finalSize = input.getExpectedResultSize();
					initHash(hash
					try (OutputStream out = buffer;
							SHA1InputStream hashed = new SHA1InputStream(hash
									input)) {
						DirCacheCheckout.getContent(repository
								new StreamLoader(() -> hashed
								out);
						if (!fh.getNewId().toObjectId()
								.equals(hash.toObjectId())) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().applyBinaryResultOidWrong
									path));
						}
					}
				}
				Files.copy(buffer.openInputStream()
						StandardCopyOption.REPLACE_EXISTING);
				break;
			default:
				break;
			}
		} finally {
			buffer.destroy();
