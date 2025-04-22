			if (pw.isReverseIndexEnabled()) {
				File tmpReverseIndexFile =
						new File(packdir
				tmpExts.put(REVERSE_INDEX
				if (!tmpReverseIndexFile.createNewFile()) {
					throw new IOException(MessageFormat.format(
							JGitText.get().cannotCreateIndexfile
							tmpReverseIndexFile.getPath()));
				}
				try (FileOutputStream fos = new FileOutputStream(
						tmpReverseIndexFile);
						FileChannel idxChannel = fos.getChannel();
						OutputStream idxStream = Channels.newOutputStream(
								idxChannel)) {
					pw.writeReverseIndex(idxStream);
					idxChannel.force(true);
				}
			}

