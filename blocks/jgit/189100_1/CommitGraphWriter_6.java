import static org.eclipse.jgit.internal.storage.commitgraph.ChangedPathFilter.TRUNCATED_EMPTY_FILTER;
import static org.eclipse.jgit.internal.storage.commitgraph.ChangedPathFilter.TRUNCATED_LARGE_FILTER;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.BLOOM_BITS_PER_ENTRY;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.BLOOM_KEY_NUM_HASHES;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.BLOOM_MAX_CHANGED_PATHS;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_BLOOM_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_BLOOM_INDEXES;
