			while (0 < n && Character.isWhitespace(buf[n - 1]))
				n--;
			String content = RawParseUtils.decode(buf, 0, n);

			throw new IOException(MessageFormat.format(JGitText.get().notARef,
					name, content), notRef);
