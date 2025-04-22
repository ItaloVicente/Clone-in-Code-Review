	private static void writeObjectSizeIndex(File objIdx
			List<PackedObjectInfo> list
		try (OutputStream os = new FileOutputStream(objIdx)) {
			PackObjectSizeIndexWriter w = PackObjectSizeIndexWriter
					.createWriter(os
			w.write(list);
		}
	}

