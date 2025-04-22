
		if (t == null)
			return id;

		if (r.hasObject(id)) {
			t.delete();
		} else {
			final File o = r.toFile(id);
			if (!t.renameTo(o)) {
				o.getParentFile().mkdir();
				if (!t.renameTo(o)) {
					if (!r.hasObject(id)) {
						t.delete();
						throw new ObjectWritingException("Unable to"
								+ " create new object: " + o);
					}
				}
			}
		}

		return id;
