
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class RefDatabaseConflictingNamesTest {

	private RefDatabase refDatabase = new RefDatabase() {
		@Override
		public Map<String
			if (ALL.equals(prefix)) {
				Map<String
				existing.put("refs/heads/a/b"
				existing.put("refs/heads/q"
				return existing;
			} else {
				return Collections.emptyMap();
			}
		}

		@Override
		public Ref peel(Ref ref) throws IOException {
			return null;
		}

		@Override
		public RefUpdate newUpdate(String name
				throws IOException {
			return null;
		}

		@Override
		public RefRename newRename(String fromName
				throws IOException {
			return null;
		}

		@Override
		public boolean isNameConflicting(String name) throws IOException {
			return false;
		}

		@Override
		public Ref getRef(String name) throws IOException {
			return null;
		}

		@Override
		public List<Ref> getAdditionalRefs() throws IOException {
			return null;
		}

		@Override
		public void create() throws IOException {
		}

		@Override
		public void close() {
		}
	};

	@Test
	public void testGetConflictingNames() throws IOException {
		assertConflictingNames("refs"
		assertConflictingNames("refs/heads"
		assertConflictingNames("refs/heads/a"

		assertNoConflictingNames("refs/heads/a/b");

		assertNoConflictingNames("refs/heads/a/d");
		assertNoConflictingNames("refs/heads/master");

		assertConflictingNames("refs/heads/a/b/c"
		assertConflictingNames("refs/heads/q/master"
	}

	private void assertNoConflictingNames(String proposed) throws IOException {
		assertEquals(Collections.emptyList()
				refDatabase.getConflictingNames(proposed));
	}

	private void assertConflictingNames(String proposed
			throws IOException {
		List<String> expected = Arrays.asList(conflicts);
		assertEquals(expected
	}
}
