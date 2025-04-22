			else {
				byte[] content = IO.readFully(dotGit);
				if (!isSymRef(content))
					throw new IOException(MessageFormat.format(
							JGitText.get().invalidGitdirRef,
							dotGit.getAbsolutePath()));
				int pathStart = 8;
				int lineEnd = RawParseUtils.nextLF(content, pathStart);
				if (content[lineEnd - 1] == '\n')
					lineEnd--;
				if (lineEnd == pathStart)
					throw new IOException(MessageFormat.format(
							JGitText.get().invalidGitdirRef,
							dotGit.getAbsolutePath()));

				String gitdirPath = RawParseUtils.decode(content, pathStart,
						lineEnd);
				File gitdirFile = new File(gitdirPath);
				if (gitdirFile.isAbsolute())
					setGitDir(gitdirFile);
				else
					setGitDir(new File(getWorkTree(), gitdirPath)
							.getCanonicalFile());
			}
