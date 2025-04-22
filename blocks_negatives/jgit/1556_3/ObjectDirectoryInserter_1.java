		if (tmp.renameTo(dst)) {
			db.addUnpackedObject(id);
			return id;
		}

		dst.getParentFile().mkdir();
		if (tmp.renameTo(dst)) {
			db.addUnpackedObject(id);
			return id;
		}

		if (db.has(id)) {
			tmp.delete();
			return id;
		}

		tmp.delete();
