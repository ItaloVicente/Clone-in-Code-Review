		if (expectDataAfterPackFooter) {
			if (!in.markSupported())
				throw new IOException(
						JGitText.get().inputStreamMustSupportMark);
			in.mark(buf.length);
		}

