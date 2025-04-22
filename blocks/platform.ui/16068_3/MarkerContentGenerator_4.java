				
				if ( markerSuppressLinksChecked ) {
					if ( shouldDisplayAfterSuppressDuplicates(markers[i] , supppressDuplicatesMap) ) {
						if (select(entry, selected, enabled, filtersAreANDed)) {
							result.add(entry);
						}
					}
				}
				else {
					if (select(entry, selected, enabled, filtersAreANDed)) {
						result.add(entry);
					}
