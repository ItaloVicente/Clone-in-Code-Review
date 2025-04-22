 * assumes it is receiving RevCommits from {@link TreeRevFilter},
 * after they have been fully buffered by {@link AbstractRevQueue}. The full
 * buffering is necessary to allow the simple loop used within our own
 * {@link #rewrite(RevCommit)} to pull completely through a strand of
 * {@link RevWalk#REWRITE} colored commits and come up with a simplification
 * that makes the DAG dense. Not fully buffering the commits first would cause
 * this loop to abort early, due to commits not being parsed and colored
 * correctly.
