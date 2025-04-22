          result.append(segments[i]);
        }

        if (hasQuery())
        {
          result.append(QUERY_SEPARATOR);
          result.append(query);
        }
      }
      else
      {
        result.append(authority);
      }

      if (hasFragment())
      {
        result.append(FRAGMENT_SEPARATOR);
        result.append(fragment);
      }
      cachedToString = result.toString();
    }
    return cachedToString;
  }

  String toString(boolean includeSimpleForm)
  {
    StringBuilder result = new StringBuilder();
    if (includeSimpleForm) {
		result.append(toString());
	}
    result.append("\n hierarchical: ");
    result.append(isHierarchical());
    result.append("\n       scheme: ");
    result.append(scheme);
    result.append("\n    authority: ");
    result.append(authority);
    result.append("\n       device: ");
    result.append(device);
    result.append("\n absolutePath: ");
    result.append(hasAbsolutePath());
    result.append("\n     segments: ");
    if (segments.length == 0) {
		result.append("<empty>");
	}
    for (int i = 0, len = segments.length; i < len; i++)
    {
      if (i > 0) {
		result.append("\n               ");
	}
      result.append(segments[i]);
    }
    result.append("\n        query: ");
    result.append(query);
    result.append("\n     fragment: ");
    result.append(fragment);
    return result.toString();
  }

  /**
   * If this URI may refer directly to a locally accessible file, as
   * determined by {@link #isFile isFile}, {@link #decode decodes} and formats
   * the URI as a pathname to that file; returns null otherwise.
   *
   * <p>If there is no authority, the format of this string is:
   * <pre>
   *   device/pathSegment1/pathSegment2...</pre>
   *
   * <p>If there is an authority, it is:
   * <pre>
   *
   * <p>However, the character used as a separator is system-dependent and
   * obtained from {@link java.io.File#separatorChar}.
   */
  public String toFileString()
  {
    if (!isFile()) {
		return null;
