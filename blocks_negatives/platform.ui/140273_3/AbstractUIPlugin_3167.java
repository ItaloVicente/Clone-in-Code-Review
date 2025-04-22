	 * This is a convenience method that simply locates the image file in within
	 * the plug-in. It will now query the ISharedImages registry first. The path
	 * is relative to the root of the plug-in, and takes into account files
	 * coming from plug-in fragments. The path may include $arg$ elements.
	 * However, the path must not have a leading "." or path separator. Clients
	 * should use a path like "icons/mysample.png" rather than
	 * "./icons/mysample.png" or "/icons/mysample.png".
