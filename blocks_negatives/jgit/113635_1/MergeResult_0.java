 * The result of merging a number of {@link Sequence} objects. These sequences
 * have one common predecessor sequence. The result of a merge is a list of
 * MergeChunks. Each MergeChunk contains either a range (a subsequence) from
 * one of the merged sequences, a range from the common predecessor or a
 * conflicting range from one of the merged sequences. A conflict will be
 * reported as multiple chunks, one for each conflicting range. The first chunk
 * for a conflict is marked specially to distinguish the border between two
 * consecutive conflicts.
