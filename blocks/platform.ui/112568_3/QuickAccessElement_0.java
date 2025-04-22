			String label = getLabel();
			if (!sortLabel.equals(label)) {
				m = p.matcher(getLabel());
				if (!m.matches()) {
					return new QuickAccessEntry(this, providerForMatching, EMPTY_INDICES, EMPTY_INDICES,
							QuickAccessEntry.MATCH_GOOD);
				}
			}
