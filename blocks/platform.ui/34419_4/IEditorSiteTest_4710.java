package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.misc.ExternalProgramImageDescriptor;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.harness.util.ArrayUtil;
import org.eclipse.ui.tests.harness.util.CallHistory;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class IEditorRegistryTest extends TestCase {
	private IEditorRegistry fReg;

	private IProject proj;

	public IEditorRegistryTest(String testName) {
		super(testName);
	}

	@Override
	public void setUp() {
		fReg = PlatformUI.getWorkbench().getEditorRegistry();
	}

	@Override
	public void tearDown() {
		if (proj != null) {
			try {
				FileUtil.deleteProject(proj);
			} catch (CoreException e) {
				TestPlugin.getDefault().getLog().log(e.getStatus());
				fail();
			}
		}
	}

	public void testGetFileEditorMappings() {
		assertTrue(ArrayUtil.checkNotNull(fReg.getFileEditorMappings()));
	}

	public void testGetEditors() throws Throwable {
		IEditorDescriptor[] editors, editors2;
		String[][] maps = { { "a.mock1", MockEditorPart.ID1 },
				{ "b.mock2", MockEditorPart.ID2 } };

		proj = FileUtil.createProject("testProject");

		for (String[] map : maps) {
			editors = fReg.getEditors(map[0]);
			assertEquals(editors.length, 1);
			assertEquals(editors[0].getId(), map[1]);
			editors2 = fReg.getEditors(FileUtil.createFile(map[0], proj)
					.getName());
			assertEquals(ArrayUtil.equals(editors, editors2), true);
		}

		String fileName = IConstants.UnknownFileName[0];
		editors = fReg.getEditors(fileName);
		assertEquals(editors.length, 0);
		editors = fReg
				.getEditors(FileUtil.createFile(fileName, proj).getName());
		assertEquals(editors.length, 0);
	}

	public void testFindEditor() {
		String id = MockEditorPart.ID1;
		IEditorDescriptor editor = fReg.findEditor(id);
		assertEquals(editor.getId(), id);

		id = IConstants.FakeID;
		editor = fReg.findEditor(id);
		assertNull(editor);
	}

	public void testGetDefaultEditor() {
		assertNotNull(fReg.getDefaultEditor());
	}

	public void testGetDefaultEditor2() {
		IEditorDescriptor editor = fReg.getDefaultEditor("a.mock1");
		assertEquals(editor.getId(), MockEditorPart.ID1);

		IEditorDescriptor editor2 = fReg.getDefaultEditor("b.mock1");
		assertEquals(editor, editor2);

		assertNull(fReg.getDefaultEditor(IConstants.UnknownFileName[0]));
	}

	public void testGetDefaultEditor3() throws Throwable {
		proj = FileUtil.createProject("testProject");

		IFile file = FileUtil.createFile("Whats up.bro", proj);
		String id = MockEditorPart.ID1;
		IDE.setDefaultEditor(file, id);
		IEditorDescriptor editor = IDE.getDefaultEditor(file);
		assertEquals(editor.getId(), id);

		file = FileUtil.createFile("ambush.mock1", proj);
		id = MockEditorPart.ID2;
		IDE.setDefaultEditor(file, id);
		editor = IDE.getDefaultEditor(file);
		assertEquals(editor.getId(), id);

		String name = "what.mock2";
		file = FileUtil.createFile(name, proj);
		editor = IDE.getDefaultEditor(file);
		assertEquals(editor, fReg.getDefaultEditor(name));

		name = IConstants.UnknownFileName[0];
		file = FileUtil.createFile(name, proj);
		assertNull(IDE.getDefaultEditor(file));
	}

	public void testGetDefaultEditor4_Bug356116() throws Throwable {
		assertNotNull(fReg.getDefaultEditor("test.bug356116"));
	}

	public void testSetDefaultEditor() throws Throwable {
		proj = FileUtil.createProject("testProject");
		IFile file = FileUtil.createFile("good.file", proj);

		String id = MockEditorPart.ID1;
		IDE.setDefaultEditor(file, id);
		IEditorDescriptor editor = IDE.getDefaultEditor(file);
		assertEquals(editor.getId(), id);

		id = MockEditorPart.ID2;
		IDE.setDefaultEditor(file, id);
		editor = IDE.getDefaultEditor(file);
		assertEquals(editor.getId(), id);

		IDE.setDefaultEditor(file, IConstants.FakeID);
		assertNull(IDE.getDefaultEditor(file));
	}

	public void testGetImageDescriptor() throws Throwable {
		proj = FileUtil.createProject("testProject");

		ImageDescriptor image1, image2;
		String fileName;

		fileName = "a.mock1";
		IFile file = FileUtil.createFile(fileName, proj);
		image1 = fReg.getImageDescriptor(fileName);
		image2 = fReg.getDefaultEditor(fileName).getImageDescriptor();
		assertEquals(image1, image2);
		assertEquals(image1, fReg.getImageDescriptor(file.getName()));

		fileName = "b.mock1";
		file = FileUtil.createFile(fileName, proj);
		assertEquals(image1, fReg.getImageDescriptor(fileName));
		assertEquals(image1, fReg.getImageDescriptor(file.getName()));

		fileName = "a.nullAndVoid";
		file = FileUtil.createFile(fileName, proj);
		image1 = fReg.getImageDescriptor(fileName);
		image2 = fReg.getImageDescriptor("b.this_is_not_good");
		assertNotNull(image1);
		if (image1 instanceof ExternalProgramImageDescriptor || image2 instanceof ExternalProgramImageDescriptor) {
			return;//If they are external we can't compare them
		}
		assertEquals(image1, image2);
		assertEquals(image2, fReg.getImageDescriptor(file.getName()));

	}

	public void testAddPropertyListener() throws Throwable {
		final String METHOD = "propertyChanged";

		IFileEditorMapping[] src = fReg.getFileEditorMappings();
		FileEditorMapping[] maps = new FileEditorMapping[src.length];
		System.arraycopy(src, 0, maps, 0, src.length);

		MockPropertyListener listener = new MockPropertyListener(fReg,
				IEditorRegistry.PROP_CONTENTS);
		fReg.addPropertyListener(listener);
		CallHistory callTrace = listener.getCallHistory();

		MockPropertyListener listener2 = new MockPropertyListener(fReg,
				IEditorRegistry.PROP_CONTENTS);
		fReg.addPropertyListener(listener2);
		CallHistory callTrace2 = listener2.getCallHistory();

		callTrace.clear();
		callTrace2.clear();
		((EditorRegistry) fReg).setFileEditorMappings(maps);
		assertEquals(callTrace.contains(METHOD), true);
		assertEquals(callTrace2.contains(METHOD), true);

		fReg.addPropertyListener(listener);

		callTrace.clear();
		((EditorRegistry) fReg).setFileEditorMappings(maps);
		assertEquals(callTrace.verifyOrder(new String[] { METHOD }), true);

		fReg.removePropertyListener(listener);
		fReg.removePropertyListener(listener2);
	}

	public void testRemovePropertyListener() {
		IFileEditorMapping[] src = fReg.getFileEditorMappings();
		FileEditorMapping[] maps = new FileEditorMapping[src.length];
		System.arraycopy(src, 0, maps, 0, src.length);

		MockPropertyListener listener = new MockPropertyListener(fReg,
				IEditorRegistry.PROP_CONTENTS);
		fReg.addPropertyListener(listener);
		fReg.removePropertyListener(listener);
		CallHistory callTrace = listener.getCallHistory();

		callTrace.clear();
		((EditorRegistry) fReg).setFileEditorMappings(maps);
		assertEquals(callTrace.contains("propertyChanged"), false);

		try {
			fReg.removePropertyListener(listener);
		} catch (Throwable e) {
			fail();
		}
	}

	public void testEditorContentTypeByFilenameWithContentType() {
		IContentType contentType = Platform.getContentTypeManager()
				.getContentType("org.eclipse.ui.tests.content-type1");
		IEditorDescriptor descriptor = fReg.getDefaultEditor(
				"content-type1.blah", contentType);
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType1Editor-fallback",
				descriptor.getId());
	}

	public void testEditorContentTypeByExtWithContentType() {
		IContentType contentType = Platform.getContentTypeManager()
				.getContentType("org.eclipse.ui.tests.content-type1");
		IEditorDescriptor descriptor = fReg.getDefaultEditor(
				"blah.content-type1", contentType);
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType1Editor-fallback",
				descriptor.getId());
	}

	public void testEditorContentTypeByExtWithoutContentType1() {
		IEditorDescriptor descriptor = fReg
				.getDefaultEditor("blah.content-type1");
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType1Editor-fallback",
				descriptor.getId());
	}

	public void testEditorContentTypeByFilenameWithoutContentType1() {
		IEditorDescriptor descriptor = fReg
				.getDefaultEditor("content-type1.blah");
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType1Editor-fallback",
				descriptor.getId());
	}

	public void testEditorContentTypeByFilenameWithoutContentType2() {
		IEditorDescriptor descriptor = fReg
				.getDefaultEditor("content-type2.blah");
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType2Editor", descriptor
				.getId());
	}

	public void testEditorContentTypeByExtWithoutContentType2() {
		IEditorDescriptor descriptor = fReg
				.getDefaultEditor("blah.content-type2");
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.contentType2Editor", descriptor
				.getId());
	}

	public void testDefaultedContentTypeEditor() {
		IEditorDescriptor descriptor = fReg
				.getDefaultEditor("foo.defaultedContentType");
		assertNotNull(descriptor);
		assertEquals("org.eclipse.ui.tests.defaultedContentTypeEditor",
				descriptor.getId());

		IEditorDescriptor[] descriptors = fReg
				.getEditors("foo.defaultedContentType");
		assertNotNull(descriptors);
		assertEquals(4, descriptors.length);

		assertEquals("org.eclipse.ui.tests.defaultedContentTypeEditor",
				descriptors[0].getId());
		assertEquals("org.eclipse.ui.tests.nondefaultedContentTypeEditor1",
				descriptors[1].getId());
		assertEquals("org.eclipse.ui.tests.nondefaultedContentTypeEditor2",
				descriptors[2].getId());
		assertEquals("org.eclipse.ui.tests.nondefaultedContentTypeEditor3",
				descriptors[3].getId());
	}

	public void testNoDefaultEditors() {
		IEditorDescriptor desc = fReg.getDefaultEditor("bogusfile.txt");

		try {
			fReg.setDefaultEditor("*.txt", null);
			IEditorDescriptor[] descriptors = fReg.getEditors("bogusfile.txt");
			for (IEditorDescriptor descriptor : descriptors) {
				assertNotNull(descriptor);
			}
		} finally {
			if (desc != null) {
				fReg.setDefaultEditor("*.txt", desc.getId());
			}
		}

	}

	public void testSwitchDefaultToExternalBug236104() {
		IEditorDescriptor htmlDescriptor = fReg.getDefaultEditor("test.html");
		assertNotNull("Default editor for html files should not be null",
				htmlDescriptor);

		IFileEditorMapping[] src = fReg.getFileEditorMappings();
		FileEditorMapping[] maps = new FileEditorMapping[src.length];
		System.arraycopy(src, 0, maps, 0, src.length);
		FileEditorMapping map = null;

		for (FileEditorMapping map2 : maps) {
			if (map2.getExtension().equals("html")) {
				map = map2;
				break;
			}
		}

		assertNotNull("Parameter map should not be null", map);

		EditorDescriptor replacementDescriptor = EditorDescriptor
				.createForProgram("notepad.exe");

		try {
			map.setDefaultEditor(replacementDescriptor);

			((EditorRegistry) fReg).setFileEditorMappings(maps);
			((EditorRegistry) fReg).saveAssociations();
			PrefUtil.savePrefs();

			IEditorDescriptor newDescriptor = fReg
					.getDefaultEditor("test.html");

			assertEquals(
					"Parameter replaceDescriptor should be the same as parameter new Descriptor",
					replacementDescriptor, newDescriptor);
			assertFalse(
					"Parameter replaceDescriptor should not be equals to htmlDescriptor",
					replacementDescriptor.equals(htmlDescriptor));
		} finally {
			src = fReg.getFileEditorMappings();
			maps = new FileEditorMapping[src.length];
			System.arraycopy(src, 0, maps, 0, src.length);
			map = null;

			for (FileEditorMapping map2 : maps) {
				if (map2.getExtension().equals("html")) {
					map = map2;
					break;
				}
			}

			assertNotNull(
					"Parameter map should not be null before setting the default editor",
					map);

			map.setDefaultEditor((EditorDescriptor) htmlDescriptor);
			((EditorRegistry) fReg).setFileEditorMappings(maps);
			((EditorRegistry) fReg).saveAssociations();
			PrefUtil.savePrefs();
		}
	}

	public void testBug308894() throws Throwable {
		FileEditorMapping newMapping = new FileEditorMapping("*.abc");
		assertNull(newMapping.getDefaultEditor());

		FileEditorMapping[] src = (FileEditorMapping[]) fReg.getFileEditorMappings();
		FileEditorMapping[] maps = new FileEditorMapping[src.length + 1];
		System.arraycopy(src, 0, maps, 0, src.length);
		maps[maps.length - 1] = newMapping;

		final Throwable[] thrownException = new Throwable[1];
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				Throwable throwable = status.getException();
				if (throwable == null) {
					thrownException[0] = new CoreException(status);
				} else {
					thrownException[0] = throwable;
				}
			}
		};
		Platform.addLogListener(listener);

		try {
			((EditorRegistry) fReg).setFileEditorMappings(maps);
			((EditorRegistry) fReg).saveAssociations();
			PrefUtil.savePrefs();
		} finally {
			((EditorRegistry) fReg).setFileEditorMappings(src);
			((EditorRegistry) fReg).saveAssociations();
			PrefUtil.savePrefs();

			Platform.removeLogListener(listener);

			if (thrownException[0] != null) {
				throw thrownException[0];
			}
		}
	}

}
