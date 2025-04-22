 * A {@link HostConfigEntryResolver} adapted specifically for JGit.
 * <p>
 * We use our own config file parser and entry resolution since the default
 * {@link org.apache.sshd.client.config.hosts.ConfigFileHostEntryResolver
 * ConfigFileHostEntryResolver} has a number of problems:
 * </p>
 * <ul>
 * <li>It does case-insensitive pattern matching. Matching in OpenSsh is
 * case-sensitive! Compare also bug 531118.</li>
 * <li>It only merges values from the global items (before the first "Host"
 * line) into the host entries. Otherwise it selects the most specific match.
 * OpenSsh processes <em>all</em> entries in the order they appear in the file
 * and whenever one matches, it updates values as appropriate.</li>
 * <li>We have to ensure that ~ replacement uses the same HOME directory as
 * JGit. Compare bug bug 526175.</li>
 * </ul>
 * Therefore, this re-uses the parsing and caching from
 * {@link OpenSshConfigFile}.
 *
