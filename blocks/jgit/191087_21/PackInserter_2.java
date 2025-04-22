		if (tmpObjSizeIdx != null) {
			PackFile realObjSizeIdx = realPack
					.create(PackExt.OBJECT_SIZE_INDEX);
			tmpObjSizeIdx.setReadOnly();
			try {
				FileUtils.rename(tmpObjSizeIdx
			} catch (IOException e) {
			}
		}

