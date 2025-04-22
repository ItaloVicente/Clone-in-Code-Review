				FileUtils.delete(f);
				if (!tmpFile.renameTo(f)) {
					throw new IOException(MessageFormat.format(
							JGitText.get().couldNotWriteFile
							tmpFile.getPath()
				}
