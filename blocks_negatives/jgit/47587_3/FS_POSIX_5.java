	 * Derives requested permission from given octal umask value as defined e.g.
	 * umask</a>.
	 * <p>
	 * The umask expected here must consist of 3 or 4 digits. Last three digits
	 * are significant here because they represent file permissions granted to
	 * the "owner", "group" and "others" (in this order).
	 * <p>
	 * Each single digit from the umask represents 3 bits of the mask standing
	 * for "<b>r</b>ead, <b>w</b>rite, e<b>x</b>ecute" permissions (in this
	 * order).
	 * <p>
	 * The possible umask values table:
	 *
	 * <pre>
	 * Value : Bits:Abbr.: Permission
	 *     0 : 000 :rwx  : read, write and execute
	 *     1 : 001 :rw   : read and write
	 *     2 : 010 :rx   : read and execute
	 *     3 : 011 :r    : read only
	 *     4 : 100 :wx   : write and execute
	 *     5 : 101 :w    : write only
	 *     6 : 110 :x    : execute only
	 *     7 : 111 :     : no permissions
	 * </pre>
	 * <p>
	 * Note, that umask value is used to "mask" the requested permissions on
	 * file creation by combining the requested permission bit with the
	 * <b>negated</b> value of the umask bit.
	 * <p>
	 * Simply speaking, if a bit is <b>not</b> set in the umask, then the
	 * appropriate right <b>will</b> be granted <b>if</b> requested. If a bit is
	 * set in the umask value, then the appropriate permission will be not
	 * granted.
	 * <p>
	 * Example:
	 * <li>umask 023 ("000 010 011" or rwx rx r) combined with the request to
	 * create an executable file with full set of permissions for everyone (777)
	 * results in the file with permissions 754 (rwx rx r).
	 * <li>umask 002 ("000 000 010" or rwx rwx rx) combined with the request to
	 * create an executable file with full set of permissions for everyone (777)
	 * results in the file with permissions 775 (rwx rwx rx).
	 * <li>umask 002 ("000 000 010" or rwx rwx rx) combined with the request to
	 * create a file without executable rights for everyone (666) results in the
	 * file with permissions 664 (rw rw r).
