  /**
   * Returns <code>true</code> if this is a relative URI, or
   * <code>false</code> if it is an absolute URI.
   */
  public boolean isRelative()
  {
    return scheme == null;
  }

  /**
   * Returns <code>true</code> if this a a hierarchical URI, or
   * <code>false</code> if it is of the generic form.
   */
  public boolean isHierarchical()
  {
    return (hashCode & HIERARICHICAL_FLAG) != 0;
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with an authority
   * component; <code>false</code> otherwise.
   */
  public boolean hasAuthority()
  {
    return isHierarchical() && authority != null;
  }

  /**
   * Returns <code>true</code> if this is a non-hierarchical URI with an
   * opaque part component; <code>false</code> otherwise.
   */
  public boolean hasOpaquePart()
  {
    return !isHierarchical();
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with a device
   * component; <code>false</code> otherwise.
   */
  public boolean hasDevice()
  {
    return device != null;
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with an
   * absolute or relative path; <code>false</code> otherwise.
   */
  public boolean hasPath()
  {
    return hasAbsolutePath() || (authority == null && device == null);
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with an
   * absolute path, or <code>false</code> if it is non-hierarchical, has no
   * path, or has a relative path.
   */
  public boolean hasAbsolutePath()
  {
    return (hashCode & ABSOLUTE_PATH_FLAG) != 0;
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with a relative
   * path, or <code>false</code> if it is non-hierarchical, has no path, or
   * has an absolute path.
   */
  public boolean hasRelativePath()
  {
    return authority == null && device == null && !hasAbsolutePath();
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with an empty
   * relative path; <code>false</code> otherwise.
   *
   * <p>Note that <code>!hasEmpty()</code> does <em>not</em> imply that this
   * URI has any path segments; however, <code>hasRelativePath &&
   * !hasEmptyPath()</code> does.
   */
  public boolean hasEmptyPath()
  {
    return authority == null && device == null && !hasAbsolutePath() &&
      segments.length == 0;
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI with a query
   * component; <code>false</code> otherwise.
   */
  public boolean hasQuery()
  {
    return query != null;
  }

  /**
   * Returns <code>true</code> if this URI has a fragment component;
   * <code>false</code> otherwise.
   */
  public boolean hasFragment()
  {
    return fragment != null;
  }

  /**
   * Returns <code>true</code> if this is a current document reference; that
   * is, if it is a relative hierarchical URI with no authority, device or
   * query components, and no path segments; <code>false</code> is returned
   * otherwise.
   */
  public boolean isCurrentDocumentReference()
  {
    return authority == null && device == null && !hasAbsolutePath() &&
      segments.length == 0 && query == null;
  }

  /**
   * Returns <code>true</code> if this is a {@link
   * #isCurrentDocumentReference() current document reference} with no
   * fragment component; <code>false</code> otherwise.
   *
   * @see #isCurrentDocumentReference()
   */
  public boolean isEmpty()
  {
    return authority == null && device == null && !hasAbsolutePath() &&
      segments.length == 0 && query == null && fragment == null;
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI that may refer
   * directly to a locally accessible file.  This is considered to be the
   * case for a file-scheme absolute URI, or for a relative URI with no query;
   * <code>false</code> is returned otherwise.
   */
  public boolean isFile()
  {
    return isHierarchical() &&
      ((isRelative() && !hasQuery()) || SCHEME_FILE.equalsIgnoreCase(scheme));
  }

  /**
   * Returns <code>true</code> if this is a platform URI, that is, an absolute,
   * hierarchical URI, with "platform" scheme, no authority, and at least two
   * segments; <code>false</code> is returned otherwise.
   * @since org.eclipse.emf.common 2.3
   */
  public boolean isPlatform()
  {
    return isHierarchical() && !hasAuthority() && segmentCount() >= 2 &&
      SCHEME_PLATFORM.equalsIgnoreCase(scheme);
  }

  /**
   * Returns <code>true</code> if this is a platform resource URI, that is,
   * a {@link #isPlatform platform URI} whose first segment is "resource";
   * <code>false</code> is returned otherwise.
   * @see #isPlatform
   * @since org.eclipse.emf.common 2.3
   */
  public boolean isPlatformResource()
  {
    return isPlatform() && "resource".equals(segments[0]);
  }

  /**
   * Returns <code>true</code> if this is a platform plug-in URI, that is,
   * a {@link #isPlatform platform URI} whose first segment is "plugin";
   * <code>false</code> is returned otherwise.
   * @see #isPlatform
   * @since org.eclipse.emf.common 2.3
   */
  public boolean isPlatformPlugin()
  {
    return isPlatform() && "plugin".equals(segments[0]);
  }

  /**
   * Returns <code>true</code> if this is an archive URI.  If so, it is also
   * hierarchical, with an authority (consisting of an absolute URI followed
   * by "!"), no device, and an absolute path.
   */
  public boolean isArchive()
  {
    return isArchiveScheme(scheme);
  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the scheme of an <a
   * href="#archive_explanation">archive URI</a>; <code>false</code>
   * otherwise.
   */
  public static boolean isArchiveScheme(String value)
  {
    return value != null && archiveSchemes.contains(value.toLowerCase());
  }

  /**
   * Returns the hash code.
   */
  @Override
  public int hashCode()
  {
    return hashCode;
  }

  /**
   * Returns <code>true</code> if <code>object</code> is an instance of
   * <code>URI</code> equal to this one; <code>false</code> otherwise.
   *
   * <p>Equality is determined strictly by comparing components, not by
   * attempting to interpret what resource is being identified.  The
   * comparison of schemes is case-insensitive.
   */
  @Override
  public boolean equals(Object object)
  {
    if (this == object) {
