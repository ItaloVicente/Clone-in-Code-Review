				sz -= n;
			}
		} catch (MissingObjectException notLocal) {
		} finally {
			pck.close();
			cur.close();
		}
	}

	private void doDeferredCheckObjs() throws IOException {
		for (PackedObjectInfo obj : deferredCheckObjs) {
			ObjectTypeAndSize info = openDatabase(obj
			if (info.type != Constants.OBJ_BLOB) {
				byte[] data = inflateAndReturn(Source.DATABASE
				verifySafeObject(obj
			} else {
				checkObjectCollision(obj
