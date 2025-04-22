
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.junit.Test;

public class CommitGraphBuilderTest {

	@Test
	public void testRepeatedChunk() throws Exception {
		byte[] buffer = new byte[GraphObjectIdIndex.FANOUT * 8];

		CommitGraphBuilder builder1 = CommitGraphBuilder.builder();
		builder1.addOidFanout(buffer);
		Exception e1 = assertThrows(CommitGraphFormatException.class
			builder1.addOidFanout(buffer);
		});
		assertEquals("commit-graph chunk id 0x4f494446 appears multiple times"
				e1.getMessage());

		CommitGraphBuilder builder2 = CommitGraphBuilder.builder();
		builder2.addOidLookUp(buffer);
		Exception e2 = assertThrows(CommitGraphFormatException.class
			builder2.addOidLookUp(buffer);
		});
		assertEquals("commit-graph chunk id 0x4f49444c appears multiple times"
				e2.getMessage());

		CommitGraphBuilder builder3 = CommitGraphBuilder.builder();
		builder3.addCommitData(buffer);
		Exception e3 = assertThrows(CommitGraphFormatException.class
			builder3.addCommitData(buffer);
		});
		assertEquals("commit-graph chunk id 0x43444154 appears multiple times"
				e3.getMessage());

		CommitGraphBuilder builder4 = CommitGraphBuilder.builder();
		builder4.addExtraList(buffer);
		Exception e4 = assertThrows(CommitGraphFormatException.class
			builder4.addExtraList(buffer);
		});
		assertEquals("commit-graph chunk id 0x45444745 appears multiple times"
				e4.getMessage());
	}

	@Test
	public void testNeededChunk() {
		byte[] buffer = new byte[GraphObjectIdIndex.FANOUT * 8];

		Exception e1 = assertThrows(CommitGraphFormatException.class
			CommitGraphBuilder.builder().addOidLookUp(buffer)
					.addCommitData(buffer).build();
		});
		assertEquals("commit-graph 0x4f494446 chunk has not been loaded"
				e1.getMessage());

		Exception e2 = assertThrows(CommitGraphFormatException.class
			CommitGraphBuilder.builder().addOidFanout(buffer)
					.addCommitData(buffer).build();
		});
		assertEquals("commit-graph 0x4f49444c chunk has not been loaded"
				e2.getMessage());

		Exception e3 = assertThrows(CommitGraphFormatException.class
			CommitGraphBuilder.builder().addOidFanout(buffer)
					.addOidLookUp(buffer).build();
		});
		assertEquals("commit-graph 0x43444154 chunk has not been loaded"
				e3.getMessage());
	}
}
