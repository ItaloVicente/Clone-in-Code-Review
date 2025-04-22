package org.eclipse.ui.tests.internal;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.EditorInputPropertyTester;
import org.mockito.Mockito;

import junit.framework.TestCase;

public class EditorInputExpressionTest extends TestCase {

	private final static String PROPERTY_CT_ID = "contentTypeId";
	private final static String ARGUMENT_KIND_OF = "kindOf";
	private final static String CT_XML = "org.eclipse.core.runtime.xml";
	private final static String CT_XMI = "org.eclipse.emf.ecore.xmi";

	public EditorInputExpressionTest(String testName) {
		super(testName);
	}

	public void testInputNegative() {
		EditorInputPropertyTester tester = new EditorInputPropertyTester();
		assertFalse(tester.test(null, null, null, null));
		assertFalse(tester.test(new Object(), null, null, null));
		assertFalse(tester.test(createEditorInput(null), null, null, null));
		assertFalse(tester.test(createEditorInput(null), PROPERTY_CT_ID, null, null));
		assertFalse(tester.test(createEditorInput(null), PROPERTY_CT_ID, null, CT_XML));
		assertFalse(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, null, null));
		assertFalse(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, new Object[] {}, null));
		assertFalse(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, new Object[] { null }, null));
		assertFalse(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, new Object[] { new Object() }, null));
		assertFalse(tester.test(createEditorInput("this_file.should_be_unknown"), PROPERTY_CT_ID, null, CT_XML));
		assertFalse(tester.test(createEditorInput("content-type2.blah"), PROPERTY_CT_ID, null, CT_XML));
	}

	public void testInputPositive() {
		EditorInputPropertyTester tester = new EditorInputPropertyTester();
		assertTrue(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, null, CT_XML));
		assertFalse(tester.test(createEditorInput("unknown.xml"), PROPERTY_CT_ID, null, CT_XMI));
		assertFalse(tester.test(createEditorInput("unknown.xmi"), PROPERTY_CT_ID, null, CT_XML));
		Object[] kindOf = new Object[] { ARGUMENT_KIND_OF };
		assertTrue(tester.test(createEditorInput("unknown.xmi"), PROPERTY_CT_ID, kindOf, CT_XML));
	}

	private IEditorInput createEditorInput(String name) {
		IEditorInput input = Mockito.mock(IEditorInput.class);
		Mockito.when(input.getName()).thenReturn(name);
		return input;
	}

}
