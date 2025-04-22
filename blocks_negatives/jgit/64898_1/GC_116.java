			boolean delete = true;
			try {
				FileUtils.rename(tmpPack, realPack);
				delete = false;
				for (Map.Entry<PackExt, File> tmpEntry : tmpExts.entrySet()) {
					File tmpExt = tmpEntry.getValue();
					tmpExt.setReadOnly();

					File realExt = nameFor(
							id, "." + tmpEntry.getKey().getExtension()); //$NON-NLS-1$
					try {
						FileUtils.rename(tmpExt, realExt);
					} catch (IOException e) {
						File newExt = new File(realExt.getParentFile(),
						if (!tmpExt.renameTo(newExt))
							newExt = tmpExt;
						throw new IOException(MessageFormat.format(
								JGitText.get().panicCantRenameIndexFile, newExt,
								realExt));
					}
				}
