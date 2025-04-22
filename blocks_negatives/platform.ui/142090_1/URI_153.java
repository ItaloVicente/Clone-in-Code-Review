    URI uri = (URI) object;

    return hashCode == uri.hashCode() &&
      equals(scheme, uri.scheme(), true) &&
      equals(authority, isHierarchical() ? uri.authority() : uri.opaquePart()) &&
      equals(device, uri.device()) &&
      equals(query, uri.query()) &&
      equals(fragment, uri.fragment()) &&
      segmentsEqual(uri);
  }

  private boolean segmentsEqual(URI uri)
  {
    if (segments.length != uri.segmentCount()) {
		return false;
