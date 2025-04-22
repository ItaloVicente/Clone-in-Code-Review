	private long loadChunkBloomData(InputStream fd
			throw new CommitGraphFormatException(
					JGitText.get().commitGraphFileIsTooLargeForJgit);
		}
		byte[] header = new byte[12];
		IO.readFully(fd

		int hashVersion = NB.decodeInt32(header
		if (hashVersion != 1) {
			throw new CommitGraphFormatException(MessageFormat.format(
					JGitText.get().requiredHashFunctionNotAvailable
					Integer.valueOf(hashVersion)));
		}
		numHashes = NB.decodeInt32(header
		bitsPerEntry = NB.decodeInt32(header

		bloomData = new byte[(int) len - header.length];
		IO.readFully(fd
		return len;
	}

