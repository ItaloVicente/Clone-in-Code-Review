
	private File getFile(String path, boolean create)
			throws PatchApplyException {
		File f = new File(getRepository().getWorkTree(), path);
		if (create) {
			try {
				File parent = f.getParentFile();
				FileUtils.mkdirs(parent, true);
				FileUtils.createNewFile(f);
			} catch (IOException e) {
				throw new PatchApplyException(MessageFormat.format(
						JGitText.get().createNewFileFailed, f), e);
			}
		}
		return f;
	}

	private void apply(Repository repository, String path, DirCache cache,
			File f, FileHeader fh) throws IOException, PatchApplyException {
		if (PatchType.BINARY.equals(fh.getPatchType())) {
			return;
		}
		boolean convertCrLf = needsCrLfConversion(f, fh);
		try (TreeWalk walk = new TreeWalk(repository)) {
			walk.setOperationType(OperationType.CHECKIN_OP);
			FileTreeIterator files = new FileTreeIterator(repository);
			int fileIdx = walk.addTree(files);
			int cacheIdx = walk.addTree(new DirCacheIterator(cache));
			files.setDirCacheIterator(walk, cacheIdx);
			walk.setFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path),
					new NotIgnoredFilter(fileIdx)));
			walk.setRecursive(true);
			if (walk.next()) {
				EolStreamType streamType = convertCrLf ? EolStreamType.TEXT_CRLF
						: walk.getEolStreamType(OperationType.CHECKOUT_OP);
				String command = walk.getFilterCommand(
						Constants.ATTR_FILTER_TYPE_SMUDGE);
				CheckoutMetadata checkOut = new CheckoutMetadata(streamType, command);
				FileTreeIterator file = walk.getTree(fileIdx,
						FileTreeIterator.class);
				if (file != null) {
					if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
						applyBinary(repository, path, f, fh,
								file::openEntryStream, file.getEntryObjectId(),
								checkOut);
					} else {
						command = walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_CLEAN);
						RawText raw;
						try (InputStream input = filterClean(repository, path,
								new FileInputStream(f), convertCrLf, command)) {
							raw = new RawText(
									IO.readWholeStream(input, 0).array());
						}
						applyText(repository, path, raw, f, fh, checkOut);
					}
					return;
				}
			}
		}
		RawText raw;
		CheckoutMetadata checkOut;
		if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
			checkOut = new CheckoutMetadata(EolStreamType.DIRECT, null);
			applyBinary(repository, path, f, fh, () -> new FileInputStream(f),
					null, checkOut);
		} else {
			if (convertCrLf) {
				try (InputStream input = EolStreamTypeUtil.wrapInputStream(
						new FileInputStream(f), EolStreamType.TEXT_LF)) {
					raw = new RawText(IO.readWholeStream(input, 0).array());
				}
				checkOut = new CheckoutMetadata(EolStreamType.TEXT_CRLF, null);
			} else {
				raw = new RawText(f);
				checkOut = new CheckoutMetadata(EolStreamType.DIRECT, null);
			}
			applyText(repository, path, raw, f, fh, checkOut);
		}
	}

	private boolean needsCrLfConversion(File f, FileHeader fileHeader)
			throws IOException {
		if (PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
			return false;
		}
		if (!hasCrLf(fileHeader)) {
			try (InputStream input = new FileInputStream(f)) {
				return RawText.isCrLfText(input);
			}
		}
		return false;
	}

	private static boolean hasCrLf(FileHeader fileHeader) {
		if (PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
			return false;
		}
		for (HunkHeader header : fileHeader.getHunks()) {
			byte[] buf = header.getBuffer();
			int hunkEnd = header.getEndOffset();
			int lineStart = header.getStartOffset();
			while (lineStart < hunkEnd) {
				int nextLineStart = RawParseUtils.nextLF(buf, lineStart);
				if (nextLineStart > hunkEnd) {
					nextLineStart = hunkEnd;
				}
				if (nextLineStart <= lineStart) {
					break;
				}
				if (nextLineStart - lineStart > 1) {
					char first = (char) (buf[lineStart] & 0xFF);
					if (first == ' ' || first == '-') {
						if (buf[nextLineStart - 2] == '\r') {
							return true;
						}
					}
				}
				lineStart = nextLineStart;
			}
		}
		return false;
	}

	private InputStream filterClean(Repository repository, String path,
			InputStream fromFile, boolean convertCrLf, String filterCommand)
			throws IOException {
		InputStream input = fromFile;
		if (convertCrLf) {
			input = EolStreamTypeUtil.wrapInputStream(input,
					EolStreamType.TEXT_LF);
		}
		if (StringUtils.isEmptyOrNull(filterCommand)) {
			return input;
		}
		if (FilterCommandRegistry.isRegistered(filterCommand)) {
			LocalFile buffer = new TemporaryBuffer.LocalFile(null);
			FilterCommand command = FilterCommandRegistry.createFilterCommand(
					filterCommand, repository, input, buffer);
			while (command.run() != -1) {
			}
			return buffer.openInputStreamWithAutoDestroy();
		}
		FS fs = repository.getFS();
		ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand,
				new String[0]);
		filterProcessBuilder.directory(repository.getWorkTree());
		filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY,
				repository.getDirectory().getAbsolutePath());
		ExecutionResult result;
		try {
			result = fs.execute(filterProcessBuilder, in);
		} catch (IOException | InterruptedException e) {
			throw new IOException(
					new FilterFailedException(e, filterCommand, path));
		}
		int rc = result.getRc();
		if (rc != 0) {
			throw new IOException(new FilterFailedException(rc, filterCommand,
					path, result.getStdout().toByteArray(4096), RawParseUtils
							.decode(result.getStderr().toByteArray(4096))));
		}
		return result.getStdout().openInputStreamWithAutoDestroy();
	}

	/**
	 * Something that can supply an {@link InputStream}.
	 */
	private interface StreamSupplier {
		InputStream load() throws IOException;
	}

	/**
	 * We write the patch result to a {@link TemporaryBuffer} and then use
	 * {@link DirCacheCheckout}.getContent() to run the result through the CR-LF
	 * and smudge filters. DirCacheCheckout needs an ObjectLoader, not a
	 * TemporaryBuffer, so this class bridges between the two, making any Stream
	 * provided by a {@link StreamSupplier} look like an ordinary git blob to
	 * DirCacheCheckout.
	 */
	private static class StreamLoader extends ObjectLoader {

		private StreamSupplier data;

		private long size;

		StreamLoader(StreamSupplier data, long length) {
			this.data = data;
			this.size = length;
		}

		@Override
		public int getType() {
			return Constants.OBJ_BLOB;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException();
		}

		@Override
		public ObjectStream openStream()
				throws MissingObjectException, IOException {
			return new ObjectStream.Filter(getType(), getSize(),
					new BufferedInputStream(data.load()));
		}
	}

	private void initHash(SHA1 hash, long size) {
		hash.update(Constants.encodedTypeString(Constants.OBJ_BLOB));
		hash.update((byte) ' ');
		hash.update(Constants.encodeASCII(size));
		hash.update((byte) 0);
	}

	private ObjectId hash(File f) throws IOException {
		SHA1 hash = SHA1.newInstance();
		initHash(hash, f.length());
		try (InputStream input = new FileInputStream(f)) {
			byte[] buf = new byte[8192];
			int n;
			while ((n = input.read(buf)) >= 0) {
				hash.update(buf, 0, n);
			}
		}
		return hash.toObjectId();
	}

	private void checkOid(ObjectId baseId, ObjectId id, ChangeType type, File f,
			String path)
			throws PatchApplyException, IOException {
		boolean hashOk = false;
		if (id != null) {
			hashOk = baseId.equals(id);
			if (!hashOk && ChangeType.ADD.equals(type)
					&& ObjectId.zeroId().equals(baseId)) {
				hashOk = Constants.EMPTY_BLOB_ID.equals(id);
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
					.format(JGitText.get().applyBinaryBaseOidWrong, path));
		}
	}

	private void applyBinary(Repository repository, String path, File f,
			FileHeader fh, StreamSupplier loader, ObjectId id,
			CheckoutMetadata checkOut)
			throws PatchApplyException, IOException {
		if (!fh.getOldId().isComplete() || !fh.getNewId().isComplete()) {
			throw new PatchApplyException(MessageFormat
					.format(JGitText.get().applyBinaryOidTooShort, path));
		}
		BinaryHunk hunk = fh.getForwardBinaryHunk();
		int start = RawParseUtils.nextLF(hunk.getBuffer(),
				hunk.getStartOffset());
		int length = hunk.getEndOffset() - start;
		SHA1 hash = SHA1.newInstance();
		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try {
			switch (hunk.getType()) {
			case LITERAL_DEFLATED:
				checkOid(fh.getOldId().toObjectId(), id, fh.getChangeType(), f,
						path);
				initHash(hash, hunk.getSize());
				try (OutputStream out = buffer;
						InputStream inflated = new SHA1InputStream(hash,
								new InflaterInputStream(
										new BinaryHunkInputStream(
												new ByteArrayInputStream(
														hunk.getBuffer(), start,
														length))))) {
					DirCacheCheckout.getContent(repository, path, checkOut,
							new StreamLoader(() -> inflated, hunk.getSize()),
							null, out);
					if (!fh.getNewId().toObjectId().equals(hash.toObjectId())) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().applyBinaryResultOidWrong,
								path));
					}
				}
				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn, f.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
				break;
			case DELTA_DEFLATED:
				byte[] base;
				try (InputStream input = loader.load()) {
					base = IO.readWholeStream(input, 0).array();
				}
				try (BinaryDeltaInputStream input = new BinaryDeltaInputStream(
						base,
						new InflaterInputStream(new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer(),
										start, length))))) {
					long finalSize = input.getExpectedResultSize();
					initHash(hash, finalSize);
					try (OutputStream out = buffer;
							SHA1InputStream hashed = new SHA1InputStream(hash,
									input)) {
						DirCacheCheckout.getContent(repository, path, checkOut,
								new StreamLoader(() -> hashed, finalSize), null,
								out);
						if (!fh.getNewId().toObjectId()
								.equals(hash.toObjectId())) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().applyBinaryResultOidWrong,
									path));
						}
					}
				}
				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn, f.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
				break;
			default:
				break;
			}
		} finally {
			buffer.destroy();
		}
	}

	private void applyText(Repository repository, String path, RawText rt,
			File f, FileHeader fh, CheckoutMetadata checkOut)
			throws IOException, PatchApplyException {
		List<ByteBuffer> oldLines = new ArrayList<>(rt.size());
		for (int i = 0; i < rt.size(); i++) {
			oldLines.add(rt.getRawString(i));
		}
		List<ByteBuffer> newLines = new ArrayList<>(oldLines);
		int afterLastHunk = 0;
		int lineNumberShift = 0;
		int lastHunkNewLine = -1;
		for (HunkHeader hh : fh.getHunks()) {

			if (hh.getNewStartLine() <= lastHunkNewLine) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException, hh));
			}
			lastHunkNewLine = hh.getNewStartLine();

			byte[] b = new byte[hh.getEndOffset() - hh.getStartOffset()];
			System.arraycopy(hh.getBuffer(), hh.getStartOffset(), b, 0,
					b.length);
			RawText hrt = new RawText(b);

			List<ByteBuffer> hunkLines = new ArrayList<>(hrt.size());
			for (int i = 0; i < hrt.size(); i++) {
				hunkLines.add(hrt.getRawString(i));
			}

			if (hh.getNewStartLine() == 0) {
				if (fh.getHunks().size() == 1
						&& canApplyAt(hunkLines, newLines, 0)) {
					newLines.clear();
					break;
				}
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException, hh));
			}
			int applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
			if (applyAt < afterLastHunk && lineNumberShift < 0) {
				applyAt = hh.getNewStartLine() - 1;
				lineNumberShift = 0;
			}
			if (applyAt < afterLastHunk) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException, hh));
			}
			boolean applies = false;
			int oldLinesInHunk = hh.getLinesContext()
					+ hh.getOldImage().getLinesDeleted();
			if (oldLinesInHunk <= 1) {
				applies = canApplyAt(hunkLines, newLines, applyAt);
				if (!applies && lineNumberShift != 0) {
					applyAt = hh.getNewStartLine() - 1;
					applies = applyAt >= afterLastHunk
							&& canApplyAt(hunkLines, newLines, applyAt);
				}
			} else {
				int maxShift = applyAt - afterLastHunk;
				for (int shift = 0; shift <= maxShift; shift++) {
					if (canApplyAt(hunkLines, newLines, applyAt - shift)) {
						applies = true;
						applyAt -= shift;
						break;
					}
				}
				if (!applies) {
					applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
					maxShift = newLines.size() - applyAt - oldLinesInHunk;
					for (int shift = 1; shift <= maxShift; shift++) {
						if (canApplyAt(hunkLines, newLines, applyAt + shift)) {
							applies = true;
							applyAt += shift;
							break;
						}
					}
				}
			}
			if (!applies) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException, hh));
			}
			lineNumberShift = applyAt - hh.getNewStartLine() + 1;
			int sz = hunkLines.size();
			for (int j = 1; j < sz; j++) {
				ByteBuffer hunkLine = hunkLines.get(j);
				if (!hunkLine.hasRemaining()) {
					applyAt++;
					continue;
				}
				switch (hunkLine.array()[hunkLine.position()]) {
				case ' ':
					applyAt++;
					break;
				case '-':
					newLines.remove(applyAt);
					break;
				case '+':
					newLines.add(applyAt++, slice(hunkLine, 1));
					break;
				default:
					break;
				}
			}
			afterLastHunk = applyAt;
		}
		if (!isNoNewlineAtEndOfFile(fh)) {
			newLines.add(null);
		}
		if (!rt.isMissingNewlineAtEnd()) {
			oldLines.add(null);
		}
		if (oldLines.equals(newLines)) {
		}

		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try {
			try (OutputStream out = buffer) {
				for (Iterator<ByteBuffer> l = newLines.iterator(); l
						.hasNext();) {
					ByteBuffer line = l.next();
					if (line == null) {
						break;
					}
					out.write(line.array(), line.position(), line.remaining());
					if (l.hasNext()) {
						out.write('\n');
					}
				}
			}
			try (OutputStream output = new FileOutputStream(f)) {
				DirCacheCheckout.getContent(repository, path, checkOut,
						new StreamLoader(buffer::openInputStream,
								buffer.length()),
						null, output);
			}
		} finally {
			buffer.destroy();
		}
		repository.getFS().setExecute(f,
				fh.getNewMode() == FileMode.EXECUTABLE_FILE);
	}

	private boolean canApplyAt(List<ByteBuffer> hunkLines,
			List<ByteBuffer> newLines, int line) {
		int sz = hunkLines.size();
		int limit = newLines.size();
		int pos = line;
		for (int j = 1; j < sz; j++) {
			ByteBuffer hunkLine = hunkLines.get(j);
			if (!hunkLine.hasRemaining()) {
				if (pos >= limit || newLines.get(pos).hasRemaining()) {
					return false;
				}
				pos++;
				continue;
			}
			switch (hunkLine.array()[hunkLine.position()]) {
			case ' ':
			case '-':
				if (pos >= limit
						|| !newLines.get(pos).equals(slice(hunkLine, 1))) {
					return false;
				}
				pos++;
				break;
			default:
				break;
			}
		}
		return true;
	}

	private ByteBuffer slice(ByteBuffer b, int off) {
		int newOffset = b.position() + off;
		return ByteBuffer.wrap(b.array(), newOffset, b.limit() - newOffset);
	}

	private boolean isNoNewlineAtEndOfFile(FileHeader fh) {
		List<? extends HunkHeader> hunks = fh.getHunks();
		if (hunks == null || hunks.isEmpty()) {
			return false;
		}
		HunkHeader lastHunk = hunks.get(hunks.size() - 1);
		byte[] buf = new byte[lastHunk.getEndOffset()
				- lastHunk.getStartOffset()];
		System.arraycopy(lastHunk.getBuffer(), lastHunk.getStartOffset(), buf,
				0, buf.length);
		RawText lhrt = new RawText(buf);
		return lhrt.getString(lhrt.size() - 1)
	}

	/**
	 * An {@link InputStream} that updates a {@link SHA1} on every byte read.
	 * The hash is supposed to have been initialized before reading starts.
	 */
	private static class SHA1InputStream extends InputStream {

		private final SHA1 hash;

		private final InputStream in;

		SHA1InputStream(SHA1 hash, InputStream in) {
			this.hash = hash;
			this.in = in;
		}

		@Override
		public int read() throws IOException {
			int b = in.read();
			if (b >= 0) {
				hash.update((byte) b);
			}
			return b;
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			int n = in.read(b, off, len);
			if (n > 0) {
				hash.update(b, off, n);
			}
			return n;
		}

		@Override
		public void close() throws IOException {
			in.close();
		}
	}
