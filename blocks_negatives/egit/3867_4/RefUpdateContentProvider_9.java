class RefUpdateContentProvider implements IStructuredContentProvider {
	public Object[] getElements(final Object inputElement) {
		if (inputElement == null)
			return new RefUpdateElement[0];

		final PushOperationResult result = (PushOperationResult) inputElement;

		final SortedMap<String, String> dstToSrc = new TreeMap<String, String>();
		for (final URIish uri : result.getURIs()) {
			if (result.isSuccessfulConnection(uri)) {
				for (final RemoteRefUpdate rru : result.getPushResult(uri)
						.getRemoteUpdates())
					dstToSrc.put(rru.getRemoteName(), rru.getSrcRef());
				break;
			}
		}

		final RefUpdateElement elements[] = new RefUpdateElement[dstToSrc
				.size()];
		int i = 0;
		for (final Entry<String, String> entry : dstToSrc.entrySet())
			elements[i++] = new RefUpdateElement(result, entry.getValue(),
					entry.getKey());
		return elements;
	}
