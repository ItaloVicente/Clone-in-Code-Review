			} else if (parent instanceof MApplication) {
				return OUTSIDE_PERSPECTIVE;
			} else if (parent instanceof MTrimBar) {
				return IN_TRIM;
			} else if (parent == null) {
				EObject container = ((EObject) curElement).eContainer();

				if (container instanceof MWindow) {
					MWindow containerWin = (MWindow) container;
					if (containerWin.getSharedElements().contains(curElement)) {
						return IN_SHARED_AREA;
					}
