package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class DirCachePathEditTest {

	static final class AddEdit extends PathEdit {

		public AddEdit(String entryPath) {
			super(entryPath);
		}

		@Override
		public void apply(DirCacheEntry ent) {
			ent.setFileMode(FileMode.REGULAR_FILE);
			ent.setLength(1);
			ent.setObjectId(ObjectId.zeroId());
		}

	}

	@Test
	public void testAddDeletePathAndTreeNormalNames() {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("b/c"));
		editor.add(new AddEdit("c/d"));
		editor.finish();
		assertEquals(3
		assertEquals("a"
		assertEquals("b/c"
		assertEquals("c/d"

		editor = dc.editor();
		editor.add(new DirCacheEditor.DeletePath("b/c"));
		editor.finish();
		assertEquals(2
		assertEquals("a"
		assertEquals("c/d"

		editor = dc.editor();
		editor.add(new DirCacheEditor.DeleteTree(""));
		editor.finish();
		assertEquals(0
	}

	@Test
	public void testAddDeleteTrickyNames() {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a/b"));
		editor.add(new AddEdit("a."));
		editor.add(new AddEdit("ab"));
		editor.finish();
		assertEquals(3
		assertEquals("a."
		assertEquals("a/b"
		assertEquals("ab"

		editor = dc.editor();
		editor.add(new DirCacheEditor.DeleteTree("a"));
		editor.finish();
		assertEquals(2
		assertEquals("a."
		assertEquals("ab"
	}
}
