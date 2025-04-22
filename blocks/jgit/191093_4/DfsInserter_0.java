	PackObjectSizeIndex writerObjectSizeIndex(DfsPackDescription pack
			List<PackedObjectInfo> packedObjs) throws IOException {
		PackObjectSizeIndex sizeIdx = null;
		try (DfsOutputStream os = db.writeFile(pack
				PackExt.OBJECT_SIZE_INDEX)) {
			PackObjectSizeIndexWriter
					.createWriter(os
					.write(packedObjs);
		}
		return sizeIdx;
	}

