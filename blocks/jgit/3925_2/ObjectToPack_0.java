
package org.eclipse.jgit.storage.pack;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class ObjectToPackTest {
	private final ObjectId id1 = ObjectId
			.fromString("1000000000000000000000000000000000000000");

	private final ObjectId id2 = ObjectId
			.fromString("2000000000000000000000000000000000000000");

	private final ObjectId id3 = ObjectId
			.fromString("3000000000000000000000000000000000000000");

	private final ObjectId id4 = ObjectId
			.fromString("4000000000000000000000000000000000000000");

	@Test
	public void testDeltaBase() {
		ObjectToPack base1 = new ObjectToPack(id1
		ObjectToPack obj1 = new ObjectToPack(id2
		ObjectToPack obj2 = new ObjectToPack(id3

		obj1.setDeltaBase(base1);
		obj2.setDeltaBase(base1);

		assertSame(base1
		assertSame(base1

		assertNull(base1.deltaNext);
		assertSame(obj2
		assertSame(obj1
		assertNull(obj1.deltaNext);

		ObjectToPack base2 = new ObjectToPack(id4
		obj1.setDeltaBase(base2);

		assertSame(obj1
		assertSame(obj2
		assertNull(obj1.deltaNext);
		assertNull(obj2.deltaNext);

		obj2.setDeltaBase(base2);
		assertNull(base1.deltaChild);
		assertSame(obj2
		assertSame(obj1
		assertNull(obj1.deltaNext);

		base1.setDeltaBase(base2);
		obj1.clearDeltaBase();
		assertSame(base1
		assertSame(obj2
		assertNull(obj2.deltaNext);
		assertNull(obj1.deltaNext);
	}
}
