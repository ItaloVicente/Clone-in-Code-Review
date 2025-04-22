
package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class NPETestScenario extends ScenariosTestCase {
	private Text text;

	Person person;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		person = new Person();
		text = new Text(getComposite(), SWT.BORDER);
	}

	public void test_InitialNullValue() {
		Person person = new Person();
		assertNull(person.getName());

		System.out
				.println("Expecting message about not being able to attach a listener");
		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(person, "name"));

		text.setText("Brad");
		text.notifyListeners(SWT.FocusOut, null);
		assertEquals("Brad", person.getName());
	}

	static class Person {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
