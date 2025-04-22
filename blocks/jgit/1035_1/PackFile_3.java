		if (UnpackedObject.LARGE_OBJECT <= sz) {
			byte[] hdr = getDeltaHeader(posSelf + hdrLen
			return new LargePackedDeltaObject(getObjectType(curs
					BinaryDelta.getResultSize(hdr)
					posSelf
		}

