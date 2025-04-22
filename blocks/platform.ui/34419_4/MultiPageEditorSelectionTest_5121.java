package org.eclipse.ui.tests.multipageeditor;

import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiEditorInput;

public class MultiEditorInputTest extends TestCase {

    public MultiEditorInputTest(String name) {
        super(name);
    }

    public void testEqualsAndHash() {
        String ea = "dummy.editor.id.A";
        String eb = "dummy.editor.id.B";
        String ec = "dummy.editor.id.C";
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IEditorInput ia = new FileEditorInput(root.getFile(new Path("/DummyProject/FileA")));
        IEditorInput ib = new FileEditorInput(root.getFile(new Path("/DummyProject/FileB")));
        IEditorInput ic = new FileEditorInput(root.getFile(new Path("/DummyProject/FileC")));
        MultiEditorInput a = new MultiEditorInput(new String[] { ea }, new IEditorInput[] { ia });
        MultiEditorInput a2 = new MultiEditorInput(new String[] { ea }, new IEditorInput[] { ia });
        MultiEditorInput b = new MultiEditorInput(new String[] { eb }, new IEditorInput[] { ib });
        MultiEditorInput abc = new MultiEditorInput(new String[] { ea, eb, ec }, new IEditorInput[] { ia, ib, ic });
        MultiEditorInput abc2 = new MultiEditorInput(new String[] { ea, eb, ec }, new IEditorInput[] { ia, ib, ic });

        assertTrue(a.equals(a));
        assertTrue(abc.equals(abc));

        assertTrue(a.equals(a2));
        assertTrue(a.hashCode() == a2.hashCode());

        assertFalse(a.equals(b));

        assertTrue(abc.equals(abc2));
        assertTrue(abc.hashCode() == abc2.hashCode());

        assertFalse(a.equals(abc));
        assertFalse(abc.equals(a));
    }
}
