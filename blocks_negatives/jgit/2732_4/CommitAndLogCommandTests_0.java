 *
 * Testing the 'commit only' option:
 *
 * I. A single file (f1.txt) specified as part of the --only/ -o option can have
 * one of the following (14) states:
 *
 * <pre>
 *        |                          | expected result
 * ---------------------------------------------------------------------
 *        | HEAD  DirCache  Worktree | HEAD  DirCache
 * ---------------------------------------------------------------------
 *  f1_1  |  -       -       c       |                => e: path unknown
 *  f1_2  |  -       c       -       |                => no changes
 *  f1_3  |  c       -       -       |  -       -
 *  f1_4  |  -       c       c       |  c       c
 *  f1_5  |  c       c       -       |  -       -
 *  f1_6  |  c       -       c       |                => no changes
 *  f1_7  |  c       c       c       |                => no changes
 * ---------------------------------------------------------------------
 *  f1_8  |  -       c       c'      |  c'      c'
 *  f1_9  |  c       -       c'      |  c'      c'
 * f1_10  |  c       c'      -       |  -       -
 * f1_11  |  c       c       c'      |  c'      c'
 * f1_12  |  c       c'      c       |                => no changes
 * f1_13  |  c       c'      c'      |  c'      c'
 * ---------------------------------------------------------------------
 * f1_14  |  c       c'      c''     |  c''     c''
 * </pre>
 *
 * II. Scenarios that do not end with a successful commit (1, 2, 6, 7, 12) have
 * to be tested with a second file (f2.txt) specified that would lead to a
 * successful commit, if it were executed separately (e.g. scenario 14).
 *
 * <pre>
 *              |                          | expected result
 * ---------------------------------------------------------------------------
 *              | HEAD  DirCache  Worktree | HEAD  DirCache
 * ---------------------------------------------------------------------------
 *  f1_1_f2_14  |  -       -       c       |                => e: path unknown
 *  f1_2_f2_14  |  -       c       -       |  -       -
 *  f1_6_f2_14  |  c       -       c       |  c       c
 *  f1_7_f2_14  |  c       c       c       |  c       c
 * ---------------------------------------------------------------------------
 * f1_12_f2_14  |  c       c'      c       |  c       c
 * </pre>
 *
 * III. All scenarios (1-14, I-II) have to be tested with different repository
 * states, to check that the --only/ -o option does not change existing content
 * (HEAD and DirCache). The following states for a file (f3.txt) not specified
 * shall be tested:
 *
 * <pre>
 *       | HEAD  DirCache
 * --------------------
 *  *_a  |  -       -
 *  *_b  |  -       c
 *  *_c  |  c       c
 *  *_d  |  c       -
 * --------------------
 *  *_e  |  c       c'
 * </pre>
 **/
