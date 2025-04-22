			return new DeltaOfsPackedObjectLoader(this, objOffset, p,
					(int) dataSize, objOffset - ofs);
		}
		case Constants.OBJ_REF_DELTA: {
			readFully(objOffset + p, ib, 0, 20, curs);
			return new DeltaRefPackedObjectLoader(this, objOffset, p + 20,
					(int) dataSize, ObjectId.fromRaw(ib));
