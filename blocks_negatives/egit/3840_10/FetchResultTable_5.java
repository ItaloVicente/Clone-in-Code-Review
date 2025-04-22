				if (r == RefUpdate.Result.REJECTED)
					return UIText.FetchResultTable_statusRejected;
				if (r == RefUpdate.Result.NO_CHANGE)
					return UIText.FetchResultTable_statusUpToDate;
				throw new IllegalArgumentException(NLS.bind(
						UIText.FetchResultTable_statusUnexpected, r));
			}

			private String safeAbbreviate(ObjectId id) {
				String abbrev = abbrevations.get(id);
				if (abbrev == null) {
					try {
						abbrev = reader.abbreviate(id).name();
					} catch (IOException cannotAbbreviate) {
						abbrev = id.name();
					}
					abbrevations.put(id, abbrev);
				}
				return abbrev;
