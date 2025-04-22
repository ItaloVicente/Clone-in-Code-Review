	private void apply(Repository repository
			File f
		boolean convertCrLf = needsCrLfConversion(f
		try (TreeWalk walk = new TreeWalk(repository)) {
			walk.setOperationType(OperationType.CHECKIN_OP);
			FileTreeIterator files = new FileTreeIterator(repository);
			int fileIdx = walk.addTree(files);
			int cacheIdx = walk.addTree(new DirCacheIterator(cache));
			files.setDirCacheIterator(walk
			walk.setFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path)
					new NotIgnoredFilter(fileIdx)));
			walk.setRecursive(true);
			if (walk.next()) {
				EolStreamType streamType = convertCrLf ? EolStreamType.TEXT_CRLF
						: walk.getEolStreamType(OperationType.CHECKOUT_OP);
				String command = walk.getFilterCommand(
						Constants.ATTR_FILTER_TYPE_SMUDGE);
				CheckoutMetadata checkOut = new CheckoutMetadata(streamType
				FileTreeIterator file = walk.getTree(fileIdx
						FileTreeIterator.class);
				if (file != null) {
					command = walk
							.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
					RawText raw;
					try (InputStream input = filterClean(repository
							new FileInputStream(f)
						raw = new RawText(IO.readWholeStream(input
					}
					apply(repository
					return;
				}
			}
		}
		RawText raw;
		CheckoutMetadata checkOut;
		if (convertCrLf) {
			try (InputStream input = EolStreamTypeUtil.wrapInputStream(
					new FileInputStream(f)
				raw = new RawText(IO.readWholeStream(input
			}
			checkOut = new CheckoutMetadata(EolStreamType.TEXT_CRLF
		} else {
			raw = new RawText(f);
			checkOut = new CheckoutMetadata(EolStreamType.DIRECT
		}
		apply(repository
	}

	private boolean needsCrLfConversion(File f
			throws IOException {
		if (!hasCrLf(fileHeader)) {
			try (InputStream input = new FileInputStream(f)) {
				return RawText.isCrLfText(input);
			}
		}
		return false;
	}

	private static boolean hasCrLf(FileHeader fileHeader) {
		if (fileHeader == null) {
			return false;
		}
		for (HunkHeader header : fileHeader.getHunks()) {
			byte[] buf = header.getBuffer();
			int hunkEnd = header.getEndOffset();
			int lineStart = header.getStartOffset();
			while (lineStart < hunkEnd) {
				int nextLineStart = RawParseUtils.nextLF(buf
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

	private InputStream filterClean(Repository repository
			InputStream fromFile
			throws IOException {
		InputStream input = fromFile;
		if (convertCrLf) {
			input = EolStreamTypeUtil.wrapInputStream(input
					EolStreamType.TEXT_LF);
		}
		if (StringUtils.isEmptyOrNull(filterCommand)) {
			return input;
		}
		if (FilterCommandRegistry.isRegistered(filterCommand)) {
			LocalFile buffer = new TemporaryBuffer.LocalFile(null);
			FilterCommand command = FilterCommandRegistry.createFilterCommand(
					filterCommand
			while (command.run() != -1) {
			}
			return buffer.openInputStreamWithAutoDestroy();
		}
		FS fs = repository.getFS();
		ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand
				new String[0]);
		filterProcessBuilder.directory(repository.getWorkTree());
		filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY
				repository.getDirectory().getAbsolutePath());
		ExecutionResult result;
		try {
			result = fs.execute(filterProcessBuilder
		} catch (IOException | InterruptedException e) {
			throw new IOException(
					new FilterFailedException(e
		}
		int rc = result.getRc();
		if (rc != 0) {
			throw new IOException(new FilterFailedException(rc
					path
							.decode(result.getStderr().toByteArray(4096))));
		}
		return result.getStdout().openInputStreamWithAutoDestroy();
	}

