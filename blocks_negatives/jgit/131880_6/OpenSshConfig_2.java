 * <p>
 * Limitations compared to the full OpenSSH 7.5 parser:
 * </p>
 * <ul>
 * <li>This parser does not handle Match or Include keywords.
 * <li>This parser does not do host name canonicalization (Jsch ignores it
 * anyway).
 * </ul>
 * <p>
 * Note that OpenSSH's readconf.c is a validating parser; Jsch's
 * ConfigRepository OTOH treats all option values as plain strings, so any
 * validation must happen in Jsch outside of the parser. Thus this parser does
 * not validate option values, except for a few options when constructing a
 * {@link org.eclipse.jgit.transport.OpenSshConfig.Host} object.
 * </p>
 * <p>
 * This config does %-substitutions for the following tokens:
 * </p>
 * <ul>
 * <li>%% - single %
 * <li>%C - short-hand for %l%h%p%r. See %p and %r below; the replacement may be
 * done partially only and may leave %p or %r or both unreplaced.
 * <li>%d - home directory path
 * <li>%h - remote host name
 * <li>%L - local host name without domain
 * <li>%l - FQDN of the local host
 * <li>%n - host name as specified in {@link #lookup(String)}
 * <li>%p - port number; replaced only if set in the config
 * <li>%r - remote user name; replaced only if set in the config
 * <li>%u - local user name
 * </ul>
 * <p>
 * If the config doesn't set the port or the remote user name, %p and %r remain
 * un-substituted. It's the caller's responsibility to replace them with values
 * obtained from the connection URI. %i is not handled; Java has no concept of a
 * "user ID".
 * </p>
