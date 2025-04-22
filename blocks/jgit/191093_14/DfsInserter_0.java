	PackObjectSizeIndex writeObjectSizeIndex(DfsPackDescription pack
			List<PackedObjectInfo> packedObjs
		PackObjectSizeIndex sizeIdx = null;
		try (DfsOutputStream os = db.writeFile(pack
				PackExt.OBJECT_SIZE_INDEX)) {
			PackObjectSizeIndexWriter
					.createWriter(os
					.write(packedObjs);
		}
		return sizeIdx;
	}

