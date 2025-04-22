			ObjectReader reader = db.newObjectReader();
			byte[] aRaw, bRaw;
			try {
				aRaw = open(reader, //
						ent.getOldPath(), //
						ent.getOldMode(), //
						ent.getOldId());
				bRaw = open(reader, //
						ent.getNewPath(), //
						ent.getNewMode(), //
						ent.getNewId());
			} finally {
				reader.release();
			}
