	public FileHeader(final byte[] headerLines
		this(headerLines
		endOffset = headerLines.length;
		int ptr = parseGitFileName(Patch.DIFF_GIT.length
		ptr = parseGitHeaders(ptr
		this.patchType = type;
		addHunk(new HunkHeader(this
	}

