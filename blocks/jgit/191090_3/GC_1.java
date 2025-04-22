			if (pconfig.isWriteObjSizeIndex()) {
				File tmpSizeIdx = new File(packdir
				tmpExts.put(OBJECT_SIZE_INDEX
				try (FileOutputStream fos = new FileOutputStream(tmpSizeIdx);
						FileChannel idxChannel = fos.getChannel();
						OutputStream idxStream = Channels
								.newOutputStream(idxChannel)) {
					pw.writeObjectSizeIndex(idxStream);
					idxChannel.force(true);
				}
			}

