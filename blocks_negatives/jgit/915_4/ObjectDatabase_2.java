 * {@link ObjectId}. Optionally an object database can reference one or more
 * alternates; other ObjectDatabase instances that are searched in addition to
 * the current database.
 * <p>
 * Databases are usually divided into two halves: a half that is considered to
 * be fast to search, and a half that is considered to be slow to search. When
 * alternates are present the fast half is fully searched (recursively through
 * all alternates) before the slow half is considered.
