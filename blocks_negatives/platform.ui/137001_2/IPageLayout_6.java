 * Wildcards are permitted in placeholder ids (but not regular view ids).
 * '*' matches any substring, '?' matches any single character.
 * Wildcards can be specified for the primary id, the secondary id, or both.
 * For example, the placeholder "someView:*" will match any occurrence of the view
 * that has primary id "someView" and that also has some non-null secondary id.
 * Note that this placeholder will not match the view if it has no secondary id,
 * since the compound id in this case is simply "someView".
