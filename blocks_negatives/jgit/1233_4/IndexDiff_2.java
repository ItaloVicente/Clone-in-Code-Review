
				if (indexEntry != null) {
					if (!file.exists()) {
						missing.add(indexEntry.getName());
						anyChanges = true;
					} else {
						if (indexEntry.isModified(root, true)) {
							modified.add(indexEntry.getName());
							anyChanges = true;
						}
