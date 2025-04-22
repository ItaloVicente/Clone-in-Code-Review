				if (members.containsKey(key)) {
					final String entryPath;
					if ("".equals(parentPath)) { //$NON-NLS-1$
						entryPath = key;
					} else {
						entryPath = parentPath + "/" + key; //$NON-NLS-1$
					}

					members.get(key).merge(entry.getValue(), entryPath,
							updateRequests);
				} else {
