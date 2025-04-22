
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevTag;
import org.junit.Test;

public class GcTagTest extends GcTestCase {
	@Test
	public void lightweightTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.getObjectDatabase().has(a));
	}

	@Test
	public void annotatedTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTag t = tr.tag("t"
		tr.lightweightTag("t"

		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.getObjectDatabase().has(t));
		assertTrue(repo.getObjectDatabase().has(a));
	}
}
