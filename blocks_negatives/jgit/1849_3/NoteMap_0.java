 * borrows/shares with the caller.
 *
 * The current implementation is not very efficient. During load it recursively
 * scans the entire note branch and indexes all annotated objects. If there are
 * more than 256 notes in the branch, and not all of them will be accessed by
 * the caller, this aggressive up-front loading probably takes too much time.
