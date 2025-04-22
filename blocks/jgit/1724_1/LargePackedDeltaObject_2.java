
			if (ok) {
				tmp.setReadOnly();
				ok = tmp.renameTo(basePath);
			}
		} finally {
			if (!ok)
				tmp.delete();
