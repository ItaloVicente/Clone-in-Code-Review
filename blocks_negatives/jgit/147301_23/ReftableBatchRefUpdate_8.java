			if (refDb.isNameConflicting(name)) {
				cmd.setResult(LOCK_FAILURE);
				ok = false;
			} else {
				int s = name.lastIndexOf('/');
				while (0 < s) {
					if (names.contains(name.substring(0, s))) {
						cmd.setResult(LOCK_FAILURE);
						ok = false;
						break;
					}
					s = name.lastIndexOf('/', s - 1);
