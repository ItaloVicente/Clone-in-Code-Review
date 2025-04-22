				try (ReadableChannel channel = repo.getObjectDatabase()
						.openFile(packDesc, PackExt.PACK)) {
					List<PackedObjectInfo> objectsInPack;
					FsckPackParser parser = new FsckPackParser(
							repo.getObjectDatabase(), channel);
					parser.setObjectChecker(objChecker);
					parser.overwriteObjectCount(packDesc.getObjectCount());
					parser.parse(pm);
					errors.getCorruptObjects()
							.addAll(parser.getCorruptObjects());
					objectsInPack = parser.getSortedObjectList(null);
					parser.verifyIndex(objectsInPack, pack.getPackIndex(ctx));
