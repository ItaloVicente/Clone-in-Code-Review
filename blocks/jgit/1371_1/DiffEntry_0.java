				entry.oldMode = FileMode.MISSING;
				entry.newMode = walk.getFileMode(0);
				entry.newPath = walk.getPathString();
			} else {
				walk.getObjectId(idBuf
				entry.oldId = AbbreviatedObjectId.fromObjectId(idBuf);

				walk.getObjectId(idBuf
				entry.newId = AbbreviatedObjectId.fromObjectId(idBuf);
