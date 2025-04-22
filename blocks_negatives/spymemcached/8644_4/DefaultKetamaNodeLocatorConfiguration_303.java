	/**
	 * Returns the socket address of a given MemcachedNode.
	 *
	 * @param node The node which we're interested in
	 * @return String the socket address of that node.
	 */
	protected String getSocketAddressForNode(MemcachedNode node) {
		String result=socketAddresses.get(node);
		if(result == null) {
			result=String.valueOf(node.getSocketAddress());
			if (result.startsWith("/")) {
				result = result.substring(1);
			}
			socketAddresses.put(node, result);
		}
		return result;
	}
