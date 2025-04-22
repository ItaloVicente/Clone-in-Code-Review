		try (DfsOutputStream out = objdb.writeFile(pack
				OBJECT_SIZE_INDEX)) {
			pw.writeObjectSizeIndex(out);
			pack.addFileExt(OBJECT_SIZE_INDEX);
			pack.setBlockSize(OBJECT_SIZE_INDEX
		}

