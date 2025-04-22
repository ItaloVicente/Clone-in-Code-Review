				for (Map.Entry<PackExt
					File tmpExt = tmpEntry.getValue();
					File realExt = nameFor(
							id
					realExt.setReadOnly();
					if (!tmpExt.renameTo(realExt)) {
						File newExt = new File(realExt.getParentFile()
						if (!tmpExt.renameTo(newExt))
							newExt = tmpExt;
						throw new IOException(MessageFormat.format(
								JGitText.get().panicCantRenameIndexFile
								realExt));
					}
