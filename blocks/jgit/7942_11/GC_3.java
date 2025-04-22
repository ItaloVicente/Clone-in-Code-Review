			if (pw.prepareIndexBitmaps(pm)) {
				File tmpBitmapIdx = new File(packdir
				tmpExts.put(BITMAP_INDEX

				if (!tmpBitmapIdx.createNewFile())
					throw new IOException(MessageFormat.format(
							JGitText.get().cannotCreateIndexfile
							tmpBitmapIdx.getPath()));

				idxChannel = new FileOutputStream(tmpBitmapIdx).getChannel();
				idxStream = Channels.newOutputStream(idxChannel);
				try {
					pw.writeBitmapIndex(idxStream);
				} finally {
					idxChannel.force(true);
					idxStream.close();
					idxChannel.close();
				}
			}

