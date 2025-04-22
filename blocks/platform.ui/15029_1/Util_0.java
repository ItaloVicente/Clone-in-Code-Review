		int rc = Math.abs(System.identityHashCode(left)) - Math.abs(System.identityHashCode(right));
		if (rc != 0) {
			return rc;
		}
		return left == right ? 0 : System.identityHashCode(left) - System.identityHashCode(right);
	}
