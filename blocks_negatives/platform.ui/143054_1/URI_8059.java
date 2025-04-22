    }
    return i;
  }

  /**
   * Static factory method based on parsing a {@link java.io.File} path
   * string.  The <code>pathName</code> is converted into an appropriate
   * form, as follows: platform specific path separators are converted to
   * <code>/<code>; the path is encoded; and a "file" scheme and, if missing,
   * a leading <code>/</code>, are added to an absolute path.  The result
   * is then parsed using {@link #createURI(String) createURI}.
   *
   * <p>The encoding step escapes all spaces, <code>#</code> characters, and
   * other characters disallowed in URIs, as well as <code>?</code>, which
   * would delimit a path from a query.  Decoding is automatically performed
   * by {@link #toFileString toFileString}, and can be applied to the values
   * returned by other accessors by via the static {@link #decode(String)
   * decode} method.
   *
   * <p>A relative path with a specified device (something like
   * <code>C:myfile.txt</code>) cannot be expressed as a valid URI.
   *
   * @exception java.lang.IllegalArgumentException if <code>pathName</code>
   * specifies a device and a relative path, or if any component of the path
   * is not valid according to {@link #validAuthority validAuthority}, {@link
   * #validDevice validDevice}, or {@link #validSegments validSegments},
   * {@link #validQuery validQuery}, or {@link #validFragment validFragment}.
   */
  public static URI createFileURI(String pathName)
  {
    File file = new File(pathName);
    String uri = File.separatorChar != '/' ? pathName.replace(File.separatorChar, SEGMENT_SEPARATOR) : pathName;
    uri = encode(uri, PATH_CHAR_HI, PATH_CHAR_LO, false);
    if (file.isAbsolute())
    {
      URI result = createURI((uri.charAt(0) == SEGMENT_SEPARATOR ? "file:" : "file:/") + uri);
      return result;
    }
    else
    {
      URI result = createURI(uri);
      if (result.scheme() != null)
      {
        throw new IllegalArgumentException("invalid relative pathName: " + pathName);
      }
      return result;
    }
  }

  /**
   * Static factory method based on parsing a workspace-relative path string.
   *
   * <p>The <code>pathName</code> must be of the form:
   * <pre>
   *   /project-name/path</pre>
   *
   * <p>Platform-specific path separators will be converted to slashes.
   * If not included, the leading path separator will be added.  The
   * result will be of this form, which is parsed using {@link #createURI(String)
   * createURI}:
   * <pre>
   *   platform:/resource/project-name/path</pre>
   *
   * <p>This scheme supports relocatable projects in Eclipse and in
   * stand-alone EMF.
   *
   * <p>Path encoding is performed only if the
   * <code>org.eclipse.emf.common.util.URI.encodePlatformResourceURIs</code>
   * system property is set to "true". Decoding can be performed with the
   * static {@link #decode(String) decode} method.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from the path is not valid according to {@link #validDevice validDevice},
   * {@link #validSegments validSegments}, {@link #validQuery validQuery}, or
   * {@link #validFragment validFragment}.
   *
   * @see org.eclipse.core.runtime.Platform#resolve
   * @see #createPlatformResourceURI(String, boolean)
   * @deprecated Use {@link #createPlatformResourceURI(String, boolean)} instead.
   */
  @Deprecated
  public static URI createPlatformResourceURI(String pathName)
  {
    return createPlatformResourceURI(pathName, ENCODE_PLATFORM_RESOURCE_URIS);
  }

  /**
   * Static factory method based on parsing a workspace-relative path string,
   * with an option to encode the created URI.
   *
   * <p>The <code>pathName</code> must be of the form:
   * <pre>
   *   /project-name/path</pre>
   *
   * <p>Platform-specific path separators will be converted to slashes.
   * If not included, the leading path separator will be added.  The
   * result will be of this form, which is parsed using {@link #createURI(String)
   * createURI}:
   * <pre>
   *   platform:/resource/project-name/path</pre>
   *
   * <p>This scheme supports relocatable projects in Eclipse and in
   * stand-alone EMF.
   *
   * <p>Depending on the <code>encode</code> argument, the path may be
   * automatically encoded to escape all spaces, <code>#</code> characters,
   * and other characters disallowed in URIs, as well as <code>?</code>,
   * which would delimit a path from a query.  Decoding can be performed with
   * the static {@link #decode(String) decode} method. It is strongly
   * recommended to specify <code>true</code> to enable encoding, unless the
   * path string has already been encoded.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from the path is not valid according to {@link #validDevice validDevice},
   * {@link #validSegments validSegments}, {@link #validQuery validQuery}, or
   * {@link #validFragment validFragment}.
   *
   * @see org.eclipse.core.runtime.Platform#resolve
   */
  public static URI createPlatformResourceURI(String pathName, boolean encode)
  {
    return createPlatformURI("platform:/resource", "platform:/resource/", pathName, encode);
  }

  /**
   * Static factory method based on parsing a plug-in-based path string,
   * with an option to encode the created URI.
   *
   * <p>The <code>pathName</code> must be of the form:
   * <pre>
   *   /plugin-id/path</pre>
   *
   * <p>Platform-specific path separators will be converted to slashes.
   * If not included, the leading path separator will be added.  The
   * result will be of this form, which is parsed using {@link #createURI(String)
   * createURI}:
   * <pre>
   *   platform:/plugin/plugin-id/path</pre>
   *
   * <p>This scheme supports relocatable plug-in content in Eclipse.
   *
   * <p>Depending on the <code>encode</code> argument, the path may be
   * automatically encoded to escape all spaces, <code>#</code> characters,
   * and other characters disallowed in URIs, as well as <code>?</code>,
   * which would delimit a path from a query.  Decoding can be performed with
   * the static {@link #decode(String) decode} method. It is strongly
   * recommended to specify <code>true</code> to enable encoding, unless the
   * path string has already been encoded.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from the path is not valid according to {@link #validDevice validDevice},
   * {@link #validSegments validSegments}, {@link #validQuery validQuery}, or
   * {@link #validFragment validFragment}.
   *
   * @see org.eclipse.core.runtime.Platform#resolve
   * @since org.eclipse.emf.common 2.3
   */
  public static URI createPlatformPluginURI(String pathName, boolean encode)
  {
    return createPlatformURI("platform:/plugin", "platform:/plugin/", pathName, encode);
  }

  private static URI createPlatformURI(String unrootedBase, String rootedBase, String pathName, boolean encode)
  {
    if (File.separatorChar != SEGMENT_SEPARATOR)
    {
      pathName = pathName.replace(File.separatorChar, SEGMENT_SEPARATOR);
    }

    if (encode)
    {
      pathName = encode(pathName, PATH_CHAR_HI, PATH_CHAR_LO, false);
    }
    URI result = createURI((pathName.charAt(0) == SEGMENT_SEPARATOR ? unrootedBase : rootedBase) + pathName);
    return result;
  }

  private URI(boolean hierarchical, String scheme, String authority,
              String device, boolean absolutePath, String[] segments,
              String query, String fragment)
  {
    int hashCode = 0;

    if (scheme != null)
    {
      hashCode ^= scheme.toLowerCase().hashCode();
    }
    if (authority != null)
    {
      hashCode ^= authority.hashCode();
    }
    if (device != null)
    {
      hashCode ^= device.hashCode();
    }
    if (query != null)
    {
      hashCode ^= query.hashCode();
    }
    if (fragment != null)
    {
      hashCode ^= fragment.hashCode();
    }

    for (String segment : segments) {
      hashCode ^= segment.hashCode();
    }

    if (hierarchical)
    {
      hashCode |= HIERARICHICAL_FLAG;
    }
    else
    {
      hashCode &= ~HIERARICHICAL_FLAG;
    }
    if (absolutePath)
    {
      hashCode |= ABSOLUTE_PATH_FLAG;
    }
    else
    {
      hashCode &= ~ABSOLUTE_PATH_FLAG;
    }
    this.hashCode = hashCode;
    this.scheme = scheme == null ? null : scheme.intern();
    this.authority = authority;
    this.device = device;
    this.segments = segments;
    this.query = query;
    this.fragment = fragment;
  }

  private static void validateURI(boolean hierarchical, String scheme,
                                    String authority, String device,
                                    boolean absolutePath, String[] segments,
                                    String query, String fragment)
  {
    if (!validScheme(scheme))
    {
      throw new IllegalArgumentException("invalid scheme: " + scheme);
    }
    if (!hierarchical && !validOpaquePart(authority))
    {
      throw new IllegalArgumentException("invalid opaquePart: " + authority);
    }
    if (hierarchical && !isArchiveScheme(scheme) && !validAuthority(authority))
    {
      throw new IllegalArgumentException("invalid authority: " + authority);
    }
    if (hierarchical && isArchiveScheme(scheme) && !validArchiveAuthority(authority))
    {
      throw new IllegalArgumentException("invalid authority: " + authority);
    }
    if (!validDevice(device))
    {
      throw new IllegalArgumentException("invalid device: " + device);
    }
    if (!validSegments(segments))
    {
      String s = segments == null ? "invalid segments: null" :
        "invalid segment: " + firstInvalidSegment(segments);
      throw new IllegalArgumentException(s);
    }
    if (!validQuery(query))
    {
      throw new IllegalArgumentException("invalid query: " + query);
    }
    if (!validFragment(fragment))
    {
      throw new IllegalArgumentException("invalid fragment: " + fragment);
    }
  }


  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the scheme component of a URI; <code>false</code> otherwise.
   *
   * <p>A valid scheme may be null or contain any characters except for the
   * following: <code>: / ? #</code>
   */
  public static boolean validScheme(String value)
  {
    return value == null || !contains(value, MAJOR_SEPARATOR_HI, MAJOR_SEPARATOR_LO);


  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the opaque part component of a URI; <code>false</code>
   * otherwise.
   *
   * <p>A valid opaque part must be non-null, non-empty, and not contain the
   * <code>#</code> character.  In addition, its first character must not be
   * <code>/</code>
   */
  public static boolean validOpaquePart(String value)
  {
    return value != null && value.indexOf(FRAGMENT_SEPARATOR) == -1 &&
    value.length() > 0 && value.charAt(0) != SEGMENT_SEPARATOR;


  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the authority component of a URI; <code>false</code> otherwise.
   *
   * <p>A valid authority may be null or contain any characters except for
   * the following: <code>/ ? #</code>
   */
  public static boolean validAuthority(String value)
  {
    return value == null || !contains(value, SEGMENT_END_HI, SEGMENT_END_LO);


  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the authority component of an <a
   * href="#archive_explanation">archive URI</a>; <code>false</code>
   * otherwise.
   *
   * <p>To be valid, the authority, itself, must be a URI with no fragment,
   * followed by the character <code>!</code>.
   */
  public static boolean validArchiveAuthority(String value)
  {
    if (value != null && value.length() > 0 &&
        value.charAt(value.length() - 1) == ARCHIVE_IDENTIFIER)
    {
      try
      {
        URI archiveURI = createURI(value.substring(0, value.length() - 1));
        return !archiveURI.hasFragment();
      }
      catch (IllegalArgumentException e)
      {
      }
    }
    return false;
  }

  /**
   * Tests whether the specified <code>value</code> would be valid as the
   * authority component of an <a href="#archive_explanation">archive
   * URI</a>. This method has been replaced by {@link #validArchiveAuthority
   * validArchiveAuthority} since the same form of URI is now supported
   * for schemes other than "jar". This now simply calls that method.
   *
   * @deprecated As of EMF 2.0, replaced by {@link #validArchiveAuthority
   * validArchiveAuthority}.
   */
  @Deprecated
  public static boolean validJarAuthority(String value)
  {
    return validArchiveAuthority(value);
  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the device component of a URI; <code>false</code> otherwise.
   *
   * <p>A valid device may be null or non-empty, containing any characters
   * except for the following: <code>/ ? #</code>  In addition, its last
   * character must be <code>:</code>
   */
  public static boolean validDevice(String value)
  {
    if (value == null) {
		return true;
