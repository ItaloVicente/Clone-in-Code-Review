 * Index values are only valid within a single {@link KetchLeader} instance
 * after it has won an election. By restricting scope to a single leader new
 * leaders do not need to traverse the entire history to determine the next
 * {@code index} for new proposals. This differs from Raft, where leader
 * election uses the log index and the term number to determine which replica
 * holds a sufficiently up-to-date log. Since Ketch uses Git objects for storage
 * of its replicated log, it keeps the term number as Raft does but uses
 * standard Git operations to imply the log index.
