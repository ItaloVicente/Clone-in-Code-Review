			if (hh.getNewStartLine() <= lastHunkNewLine) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException
			}
			lastHunkNewLine = hh.getNewStartLine();

