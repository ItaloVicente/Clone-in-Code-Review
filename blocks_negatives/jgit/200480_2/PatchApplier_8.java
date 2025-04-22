				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException, hh));
			}
			lineNumberShift = applyAt - hh.getNewStartLine() + 1;
			int sz = hunkLines.size();
			for (int j = 1; j < sz; j++) {
				ByteBuffer hunkLine = hunkLines.get(j);
				if (!hunkLine.hasRemaining()) {
					applyAt++;
					lastWasRemoval = false;
