			if (type != null) {
				if (matchTypeChildren) {
					if(!marker.isSubtypeOf(type)) {
						return null;
					}
				} else {
					if(!type.equals(marker.getType())) {
						return null;
					}
				}
