 *   string is a valid OpenPGP User ID (RFC4880 5.1.1). The raw User ID is
 *   always available as {@link #getUserId()}, but {@link #getEmailAddress()}
 *   may return null.</li>
 * <li>The raw text from which the identity was parsed is available with {@link
 *   #getRaw()}. This is necessary for losslessly reconstructing the signed push
 *   certificate payload.</li>
