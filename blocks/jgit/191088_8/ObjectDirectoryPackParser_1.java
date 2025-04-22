			if (pconfig.isWriteObjSizeIndex()) {
				tmpObjSizeIdx = new File(db.getDirectory()
						+ PackExt.OBJECT_SIZE_INDEX.getExtension());
				writeSizeIdx(pconfig.getMinBytesForObjSizeIndex());
				tmpObjSizeIdx.setReadOnly();
			}

