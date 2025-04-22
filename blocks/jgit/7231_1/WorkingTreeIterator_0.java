				if (ent != null && compareMetadata(ent) == MetadataDiff.EQUAL) {
					contentIdOffset = i.idOffset();
					contentIdFromPtr = ptr;
					return contentId = i.idBuffer();
				}
				contentIdOffset = 0;
