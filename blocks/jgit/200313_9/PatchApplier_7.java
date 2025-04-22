				apply(fh.getNewPath()
			}
				break;
			case MODIFY:
				apply(fh.getOldPath()
						getFile(fh.getOldPath())
				break;
			case DELETE:
				if (!inCore()) {
					File old = getFile(fh.getOldPath());
					if (!old.delete())
						throw new IOException(MessageFormat.format(
								JGitText.get().cannotDeleteFile
