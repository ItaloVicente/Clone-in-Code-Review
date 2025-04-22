				case ADD: {
					File f = getFile(fh.getNewPath());
					if (f != null) {
						try {
							FileUtils.mkdirs(f.getParentFile(), true);
							FileUtils.createNewFile(f);
						} catch (IOException e) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().createNewFileFailed, f), e);
