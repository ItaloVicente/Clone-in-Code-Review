 *
 * A system property <em>jgit.junit.usemmap</em> defines whether memory mapping
 * is used. Memory mapping has an effect on the file system, in that memory
 * mapped files in java cannot be deleted as long as they mapped arrays have not
 * been reclaimed by the garbage collector. The programmer cannot control this
 * with precision, though hinting using <em>{@link java.lang.System#gc}</em>
 * often helps.
