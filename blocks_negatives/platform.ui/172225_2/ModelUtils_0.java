		} else {
			if( elements.size() >= 1 ) {
				if( elements.size() > 1 ) {
					System.err.println("The feature is single valued but a list of values is passed in.");
				}
				MApplicationElement e = elements.get(0);
				eContainer.eSet(feature, e);
				return Collections.singletonList(e);
