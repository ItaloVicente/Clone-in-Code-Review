		if (relationship.equals(VAL_LEFT)) {
			intRelation = IPageLayout.LEFT;
		} else if (relationship.equals(VAL_RIGHT)) {
			intRelation = IPageLayout.RIGHT;
		} else if (relationship.equals(VAL_TOP)) {
			intRelation = IPageLayout.TOP;
		} else if (relationship.equals(VAL_BOTTOM)) {
			intRelation = IPageLayout.BOTTOM;
		} else if (relationship.equals(VAL_STACK)) {
			stack = true;
		} else if (relationship.equals(VAL_FAST)) {
			fast = true;
		} else {
			return false;
		}
