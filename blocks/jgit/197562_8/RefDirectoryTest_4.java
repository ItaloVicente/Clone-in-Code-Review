		if (refDir.getRefCache().isPresent()) {
			RefCache c = refDir.getRefCache().get();
			try {
				Ref r = refDir.exactRef(name);
				if (r != null) {
					c.insert(r);
				} else {
					c.delete(name);
				}
			} catch (IOException e) {
			}
		}
