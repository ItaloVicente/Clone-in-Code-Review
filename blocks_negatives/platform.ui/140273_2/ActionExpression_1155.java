		 * Extract the object class tests from the expression. This allows
		 * clients (e.g. the decorator manager) to handle object class testing
		 * in a more optimized way. This method extracts the objectClass test
		 * from the expression and returns the object classes. The expression is
		 * not changed and a <code>null</code> is returned if no object class
		 * is found.
