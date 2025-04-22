	 * @param href
	 *            the help resource
	 * @param documentOnly
	 *            if <code>true</code>, the resulting URL must point at the
	 *            document referenced by href. Otherwise, it can be a URL that
	 *            contains additional elements like navigation that the help
	 *            system adds to the document.
	 * @return the fully resolved URL of the help resource or <code>null</code>
	 *         if not supported. Help systems that use application servers
	 *         typically return URLs with http: protocol. Simple help system
	 *         implementations can return URLs with file: protocol.
