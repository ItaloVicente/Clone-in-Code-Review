				if (findInReference && notFound) {
					if (revision.getRefCount() > 0) {
						for (int j = 0; j < revision.getRefCount(); j++) {
							Ref ref = revision.getRef(j);
							String refName = ref.getName();
							refName = Repository.shortenRefName(refName);
							if (ignoreCase)
								refName = refName.toLowerCase();
							if (refName.indexOf(findPattern) != -1) {
								totalMatches++;
								findResults.add(i, revision);
								notFound = false;
							}
						}
					}
				}

