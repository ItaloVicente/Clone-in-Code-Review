
				if ( markerSuppressLinksChecked ) {
					if ( shouldDisplayAfterSuppressDuplicates(markers[i] , supppressDuplicatesMap) ) {
						 if (select(entry, selected, filters, andFilters) ) {
							result.add(entry);
						 }
					}
				}
				else {
					if (select(entry, selected, filters, andFilters) ) {
						result.add(entry);
					}
