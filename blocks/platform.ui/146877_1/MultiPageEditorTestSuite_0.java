package org.eclipse.ui.tests.multipageeditor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.ErrorEditorPart;
import org.eclipse.ui.internal.part.NullEditorInput;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.junit.Test;

public class MultiPageEditorPartTest extends UITestCase {

	public MultiPageEditorPartTest(String testName) {
		super(testName);
	}

	@Override
	protected void doTearDown() throws Exception {
		TestMultiPageEditorThrowingPartInitException.resetSpy();
		super.doTearDown();
	}

	@Test
	public void testDisposalWithoutSuccessfulInitialization() throws Exception {
		IEditorPart editor = openTestWindow().getActivePage().openEditor(new NullEditorInput(),
				"org.eclipse.ui.tests.multipageeditor.TestMultiPageEditorThrowingPartInitException"); //$NON-NLS-1$

		assertThat(editor, instanceOf(ErrorEditorPart.class));
		assertThat("The editor should have been diposed by CompatibilityPart",
				TestMultiPageEditorThrowingPartInitException.disposeCalled, is(true));
		assertThat("No exception should have been thrown while disposing",
				TestMultiPageEditorThrowingPartInitException.exceptionWhileDisposing, is(nullValue()));
	}

}
