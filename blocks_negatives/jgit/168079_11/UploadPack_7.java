	 * @param adv
	 *            the advertisement formatter.
	 * @param serviceName
	 *            if not null, also output "# service=serviceName" followed by a
	 *            flush packet before the advertisement. This is required
	 *            in v0 of the HTTP protocol, described in Git's
	 *            Documentation/technical/http-protocol.txt.
	 * @throws java.io.IOException
	 *            the formatter failed to write an advertisement.
	 * @throws org.eclipse.jgit.transport.ServiceMayNotContinueException
	 *            the hook denied advertisement.
