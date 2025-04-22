			ObjectInserter oi = db.newObjectInserter();
			try {
				ObjectId newsha1 = oi.insert(Constants.OBJ_BLOB
				oi.flush();
				if (!newsha1.equals(sha1))
					modified = true;
				sha1 = newsha1;
			} finally {
				oi.release();
			}
