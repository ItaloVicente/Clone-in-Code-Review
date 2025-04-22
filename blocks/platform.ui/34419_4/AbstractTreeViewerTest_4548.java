
package org.eclipse.jface.tests.preferences;


import junit.framework.TestCase;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class StringFieldEditorTest extends TestCase {

	private Shell shell;
	private StringFieldEditor stringFieldEditor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		shell = new Shell();

		stringFieldEditor = new StringFieldEditor("name", "label", shell);
	}

	public void testSetLabelText() {
		stringFieldEditor.setLabelText("label text");
		assertEquals("label text", stringFieldEditor.getLabelText());

		stringFieldEditor.setLabelText("label text");
		assertEquals("label text", stringFieldEditor.getLabelText());
	}

	public void testLoad() {
		PreferenceStore myPreferenceStore = new PreferenceStore();
		stringFieldEditor.setPreferenceName("name");
		stringFieldEditor.setPreferenceStore(myPreferenceStore);

		myPreferenceStore.setDefault("name", "foo");
		stringFieldEditor.load();
		assertEquals(stringFieldEditor.getStringValue(), "foo");

		myPreferenceStore.setDefault("name", "foo");
		myPreferenceStore.setValue("name", "bar");
		stringFieldEditor.load();
		assertEquals(stringFieldEditor.getStringValue(), "bar");
	}

	public void testLoadDefault() {
		PreferenceStore myPreferenceStore = new PreferenceStore();
		stringFieldEditor.setPreferenceName("name");
		stringFieldEditor.setPreferenceStore(myPreferenceStore);

		myPreferenceStore.setDefault("name", "foo");
		myPreferenceStore.setValue("name", "bar");
		stringFieldEditor.loadDefault();
		assertEquals(stringFieldEditor.getStringValue(), "foo");
	}

	public void testSetValueInWidget() {
		PreferenceStore myPreferenceStore = new PreferenceStore();
		stringFieldEditor.setPreferenceName("name");
		stringFieldEditor.setPreferenceStore(myPreferenceStore);

		myPreferenceStore.setValue("name", "foo");
		stringFieldEditor.load();
		assertEquals(stringFieldEditor.getStringValue(), "foo");

		Text text = stringFieldEditor.getTextControl(shell);
		text.setText("bar");
		assertEquals(stringFieldEditor.getStringValue(), "bar");
	}

	public void testSetValueInEditor() {
		PreferenceStore myPreferenceStore = new PreferenceStore();
		stringFieldEditor.setPreferenceName("name");
		stringFieldEditor.setPreferenceStore(myPreferenceStore);

		myPreferenceStore.setValue("name", "foo");
		stringFieldEditor.load();
		assertEquals(stringFieldEditor.getStringValue(), "foo");

		stringFieldEditor.setStringValue("bar");
		Text text = stringFieldEditor.getTextControl(shell);
		assertEquals(text.getText(), "bar");
		assertEquals(stringFieldEditor.getStringValue(), "bar");
	}

	public void testBug289599() {
		PreferenceStore store = new PreferenceStore();
		store.setDefault("foo", "bar");
		assertEquals("bar", store.getString("foo"));
		store.setValue("foo", "???");
		assertEquals("???", store.getString("foo"));
		IPropertyChangeListener listener = new  IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				assertEquals("foo", event.getProperty());
				assertEquals("???", event.getOldValue());
				assertEquals("bar", event.getNewValue());
			}
		};
		store.addPropertyChangeListener(listener);
		store.setToDefault("foo");
		store.removePropertyChangeListener(listener);
		assertEquals("bar", store.getString("foo"));
		IPropertyChangeListener failingListener = new  IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				fail("1.0");
			}
		};
		store.addPropertyChangeListener(failingListener);
		store.setToDefault("foo");
		store.removePropertyChangeListener(failingListener);
		assertEquals("bar", store.getString("foo"));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}

