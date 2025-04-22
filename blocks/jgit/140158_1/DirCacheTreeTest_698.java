package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.errors.DirCacheNameConflictException;
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

	private static final class RecordingEdit extends PathEdit {
		final List<DirCacheEntry> entries = new ArrayList<>();

		public RecordingEdit(String entryPath) {
			super(entryPath);
		}

		@Override
		public void apply(DirCacheEntry ent) {
			entries.add(ent);
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
		editor.add(new AddEdit("a-"));
		editor.add(new AddEdit("ab"));
		editor.finish();
		assertEquals(3

		assertEquals("a-"
		assertEquals("a/b"
		assertEquals("ab"

		editor = dc.editor();

		editor.add(new DirCacheEditor.DeleteTree("a"));
		editor.finish();
		assertEquals(2
		assertEquals("a-"
		assertEquals("ab"
	}

	@Test
	public void testPathEditShouldBeCalledForEachStage() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheBuilder builder = new DirCacheBuilder(dc
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.finish();

		DirCacheEditor editor = dc.editor();
		RecordingEdit recorder = new RecordingEdit("a");
		editor.add(recorder);
		editor.finish();

		List<DirCacheEntry> entries = recorder.entries;
		assertEquals(3
		assertEquals(DirCacheEntry.STAGE_1
		assertEquals(DirCacheEntry.STAGE_2
		assertEquals(DirCacheEntry.STAGE_3
	}

	@Test
	public void testFileReplacesTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("b/c"));
		editor.add(new AddEdit("b/d"));
		editor.add(new AddEdit("e"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("b"));
		editor.finish();

		assertEquals(3
		assertEquals("a"
		assertEquals("b"
		assertEquals("e"

		dc.clear();
		editor = dc.editor();
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/c"));
		editor.add(new AddEdit("A0c"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("A"));
		editor.finish();
		assertEquals(3
		assertEquals("A"
		assertEquals("A.c"
		assertEquals("A0c"
	}

	@Test
	public void testTreeReplacesFile() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("ab"));
		editor.add(new AddEdit("b"));
		editor.add(new AddEdit("e"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("b/c/d/f"));
		editor.add(new AddEdit("b/g/h/i"));
		editor.finish();

		assertEquals(5
		assertEquals("a"
		assertEquals("ab"
		assertEquals("b/c/d/f"
		assertEquals("b/g/h/i"
		assertEquals("e"
	}

	@Test
	public void testDuplicateFiles() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a"));

		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("a a"
			assertEquals("a"
			assertEquals("a"
		}
	}

	@Test
	public void testFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a/b").setReplace(false));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("a a/b"
			assertEquals("a"
			assertEquals("a/b"
		}

		editor = dc.editor();
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/c").setReplace(false));
		editor.add(new AddEdit("A0c"));
		editor.add(new AddEdit("A"));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("A A/c"
			assertEquals("A"
			assertEquals("A/c"
		}

		editor = dc.editor();
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/b/c/d").setReplace(false));
		editor.add(new AddEdit("A/b/c"));
		editor.add(new AddEdit("A0c"));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("A/b/c A/b/c/d"
			assertEquals("A/b/c"
			assertEquals("A/b/c/d"
		}
	}

	private static DirCacheEntry createEntry(String path
		DirCacheEntry entry = new DirCacheEntry(path
		entry.setFileMode(FileMode.REGULAR_FILE);
		return entry;
	}
}
