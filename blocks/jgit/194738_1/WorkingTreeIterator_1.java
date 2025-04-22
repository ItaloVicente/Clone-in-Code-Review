			IgnoreNode parentNode = infoExclude != null ? infoExclude
					: coreExclude;

			IgnoreNode r;
			if (entry != null) {
				r = super.load(parentNode);
				if (r == null) {
					return null;
				}
			} else {
				return parentNode;
			}
			return r.getRules().isEmpty() ? parentNode : r;
