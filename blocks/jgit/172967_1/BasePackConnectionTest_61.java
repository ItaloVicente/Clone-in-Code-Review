package org.eclipse.jgit.transport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.junit.Test;

public class BasePackConnectionTest {

	@Test
	public void testExtractSymRefsFromCapabilities() {
		final Map<String
				.extractSymRefsFromCapabilities(
						Arrays.asList("symref=HEAD:refs/heads/main"
								"symref=refs/heads/sym:refs/heads/other"));

		assertEquals(2
		assertEquals("refs/heads/main"
		assertEquals("refs/heads/other"
	}

	@Test
	public void testUpdateWithSymRefsAdds() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("HEAD"

		BasePackConnection.updateWithSymRefs(refMap

		assertThat(refMap
		final Ref headRef = refMap.get("HEAD");
		assertThat(headRef
		final SymbolicRef headSymRef = (SymbolicRef) headRef;
		assertEquals("HEAD"
		assertSame(mainRef
	}

	@Test
	public void testUpdateWithSymRefsReplaces() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("HEAD"
				mainRef.getObjectId()));
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("HEAD"

		BasePackConnection.updateWithSymRefs(refMap

		assertThat(refMap
		final Ref headRef = refMap.get("HEAD");
		assertThat(headRef
		final SymbolicRef headSymRef = (SymbolicRef) headRef;
		assertEquals("HEAD"
		assertSame(mainRef
	}

	@Test
	public void testUpdateWithSymRefsWithIndirectsAdds() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("refs/heads/sym3"
		symRefs.put("refs/heads/sym1"
		symRefs.put("refs/heads/sym2"

		BasePackConnection.updateWithSymRefs(refMap

		assertThat(refMap
		final Ref sym1Ref = refMap.get("refs/heads/sym1");
		assertThat(sym1Ref
		final SymbolicRef sym1SymRef = (SymbolicRef) sym1Ref;
		assertEquals("refs/heads/sym1"
		assertSame(mainRef

		assertThat(refMap
		final Ref sym2Ref = refMap.get("refs/heads/sym2");
		assertThat(sym2Ref
		final SymbolicRef sym2SymRef = (SymbolicRef) sym2Ref;
		assertEquals("refs/heads/sym2"
		assertSame(sym1SymRef

		assertThat(refMap
		final Ref sym3Ref = refMap.get("refs/heads/sym3");
		assertThat(sym3Ref
		final SymbolicRef sym3SymRef = (SymbolicRef) sym3Ref;
		assertEquals("refs/heads/sym3"
		assertSame(sym2SymRef
	}

	@Test
	public void testUpdateWithSymRefsWithIndirectsReplaces() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("refs/heads/sym1"
				Ref.Storage.LOOSE
		refMap.put("refs/heads/sym2"
				Ref.Storage.LOOSE
		refMap.put("refs/heads/sym3"
				Ref.Storage.LOOSE
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("refs/heads/sym3"
		symRefs.put("refs/heads/sym1"
		symRefs.put("refs/heads/sym2"

		BasePackConnection.updateWithSymRefs(refMap

		assertThat(refMap
		final Ref sym1Ref = refMap.get("refs/heads/sym1");
		assertThat(sym1Ref
		final SymbolicRef sym1SymRef = (SymbolicRef) sym1Ref;
		assertEquals("refs/heads/sym1"
		assertSame(mainRef

		assertThat(refMap
		final Ref sym2Ref = refMap.get("refs/heads/sym2");
		assertThat(sym2Ref
		final SymbolicRef sym2SymRef = (SymbolicRef) sym2Ref;
		assertEquals("refs/heads/sym2"
		assertSame(sym1SymRef

		assertThat(refMap
		final Ref sym3Ref = refMap.get("refs/heads/sym3");
		assertThat(sym3Ref
		final SymbolicRef sym3SymRef = (SymbolicRef) sym3Ref;
		assertEquals("refs/heads/sym3"
		assertSame(sym2SymRef
	}

	@Test
	public void testUpdateWithSymRefsIgnoresSelfReference() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("refs/heads/sym1"

		BasePackConnection.updateWithSymRefs(refMap

		assertEquals(2
		assertThat(refMap
	}

	@Test
	public void testUpdateWithSymRefsIgnoreCircularReference() {
		final Ref mainRef = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				"refs/heads/main"
						"0000000000000000000000000000000000000001"));

		final Map<String
		refMap.put(mainRef.getName()
		refMap.put("refs/heads/other"
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
						ObjectId.fromString(
								"0000000000000000000000000000000000000002")));

		final Map<String
		symRefs.put("refs/heads/sym2"
		symRefs.put("refs/heads/sym1"

		BasePackConnection.updateWithSymRefs(refMap

		assertEquals(2
		assertThat(refMap
		assertThat(refMap
	}
}
