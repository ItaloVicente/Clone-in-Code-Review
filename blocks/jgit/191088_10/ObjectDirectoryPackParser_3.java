		if (pconfig.isWriteObjSizeIndex() && tmpObjSizeIdx != null) {
			PackFile finalObjSizeIdx = finalPack
					.create(PackExt.OBJECT_SIZE_INDEX);
			try {
				FileUtils.rename(tmpObjSizeIdx
						StandardCopyOption.ATOMIC_MOVE);
			} catch (IOException e) {
				cleanupTemporaryFiles();
				keep.unlock();
				if (!finalPack.delete())
					finalPack.deleteOnExit();
				if (!finalIdx.delete()) {
					finalIdx.deleteOnExit();
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotMoveIndexTo
								finalObjSizeIdx)
						e);
			}
		}

