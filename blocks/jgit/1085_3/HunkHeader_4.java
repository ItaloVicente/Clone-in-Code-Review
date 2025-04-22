	HunkHeader(final FileHeader fh
		this(fh
		this.editList = editList;
		endOffset = startOffset;
		nContext = 0;
		if (editList.isEmpty()) {
			newStartLine = 0;
			newLineCount = 0;
		} else {
			newStartLine = editList.get(0).getBeginB();
			Edit last = editList.get(editList.size() - 1);
			newLineCount = last.getEndB() - newStartLine;
		}
	}

