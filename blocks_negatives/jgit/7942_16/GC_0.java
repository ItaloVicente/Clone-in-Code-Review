				if (!tmpIdx.renameTo(realIdx)) {
					File newIdx = new File(realIdx.getParentFile(),
					if (!tmpIdx.renameTo(newIdx))
						newIdx = tmpIdx;
					throw new IOException(MessageFormat.format(
							JGitText.get().panicCantRenameIndexFile, newIdx,
							realIdx));
