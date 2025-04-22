
package org.eclipse.jface.tests.databinding.scenarios;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.tests.databinding.BindingTestSuite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;

public class ComboUpdatingTest extends ScenariosTestCase {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}


	private Combo comboEditable;

	private static final String PROP_TEXT = "text";
	private String text = "Hello, world";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private static final String PROP_CHOICES = "choices";
	private List choices = new ArrayList();
	 {
		choices.add("Banana");
		choices.add("Apple");
		choices.add("Mango");
	}

	public List getChoices() {
		return choices;
	}

	public void setChoices(List choices) {
		this.choices = choices;
		firePropertyChange(PROP_CHOICES, null, null);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getComposite().setLayout(new FillLayout());
		comboEditable = new Combo(getComposite(), SWT.DROP_DOWN);
	}


	private static final String NEXT = "Next";
	public void testBindText() throws Exception {
        getDbc().bindValue(SWTObservables.observeText(comboEditable), BeansObservables.observeValue(this, "text"));
		spinEventLoop(0);
		assertEquals("Should find value of text", text, comboEditable.getText());
		comboEditable.setText(NEXT);
		spinEventLoop(0);
		assertEquals("Should find new value in text", NEXT, text);
	}

	public void testBindItems_listHasSameItems_editable() throws Exception {
		if (BindingTestSuite.failingTestsDisabled(this)) {
			return;
		}
		text = "Apple";

        getDbc().bindValue(SWTObservables.observeText(comboEditable), BeansObservables.observeValue(this, PROP_TEXT));

		spinEventLoop(0);
		assertEquals("Should find value of text", text, comboEditable.getText());

        IObservableList list = new WritableList(getChoices(), null);
        getDbc().bindList(SWTObservables.observeItems(comboEditable), list);

		spinEventLoop(0);
		int position = 0;
		for (Iterator choicesIter = choices.iterator(); choicesIter.hasNext();) {
			String element = (String) choicesIter.next();
			assertEquals(element, comboEditable.getItem(position));
			++position;
		}
		assertEquals("Should find value of combo.getText()", "", comboEditable.getText());
		comboEditable.setText("Banana");
		spinEventLoop(0);
		assertEquals("Should find value of text", "Banana", text);
	}


	public void testBindItems_listHasDifferentItems_editable() throws Exception {
		if (BindingTestSuite.failingTestsDisabled(this)) {
			return;
		}

        getDbc().bindValue(SWTObservables.observeText(comboEditable), BeansObservables.observeValue(this, PROP_TEXT));

		spinEventLoop(0);
		assertEquals("Should find value of text", text, comboEditable.getText());

        IObservableList list = new WritableList(new ArrayList(), String.class);
        list.addAll(getChoices());
        getDbc().bindList(SWTObservables.observeItems(comboEditable), list);

		spinEventLoop(0);
		int position = 0;
		for (Iterator choicesIter = choices.iterator(); choicesIter.hasNext();) {
			String element = (String) choicesIter.next();
			assertEquals(element, comboEditable.getItem(position));
			++position;
		}
		assertEquals("Should find value of combo.getText()", "", comboEditable.getText());
		comboEditable.setText("Banana");
		spinEventLoop(0);
		assertEquals("Should find value of text", "Banana", text);
	}


}
