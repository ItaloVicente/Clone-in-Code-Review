			}
			boolean mismatch = false;
			if (hadNull) {
				mismatch = mapping != null;
			} else {
				if (repositoryMapping == null) {
					mismatch = true;
				} else {
					mismatch = mapping == null
							|| mapping.getRepository() != repositoryMapping
									.getRepository();
				}
			}
			if (mismatch) {
				if (warn) {
