 * {@link RefTree} representing the desired target repository state to the
 * {@code refs/txn/accepted} branch on each of the replicas. Once a majority has
 * succeeded, the leader commits the state by either pushing the
 * {@code refs/txn/accepted} value to {@code refs/txn/committed} (for
 * Ketch-aware replicas) or by pushing updates to {@code refs/heads/master},
 * etc. for stock Git replicas.
