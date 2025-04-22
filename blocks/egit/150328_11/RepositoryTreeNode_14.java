		if (considerParent(myType)) {
			if (myParent == null) {
				if (other.myParent != null) {
					return false;
				}
			} else if (!myParent.equals(other.myParent)) {
