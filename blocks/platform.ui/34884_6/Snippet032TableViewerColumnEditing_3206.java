
package org.eclipse.jface.examples.databinding.snippets;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.util.JFaceProperties;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet031JFaceObservable {

	public static final String NAME_PROPERTY = "name_property";

	public static void main(String[] args) {
		Display display = new Display();
		final ViewModel viewModel = new ViewModel();

		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final Shell shell = new View(viewModel).createShell();
				Display display = Display.getCurrent();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		System.out.println("person.getName() = "
				+ viewModel.getPerson().getName());
	}

	public static class Person extends EventManager {
		String name = "HelloWorld";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			fireChange(new PropertyChangeEvent(this, NAME_PROPERTY, this.name,
					this.name = name));
		}

		public void addPropertyChangeListener(IPropertyChangeListener listener) {
			addListenerObject(listener);
		}

		public void removePropertyChangeListener(
				IPropertyChangeListener listener) {
			removeListenerObject(listener);
		}

		private void fireChange(PropertyChangeEvent event) {
			final Object[] list = getListeners();
			for (int i = 0; i < list.length; ++i) {
				((IPropertyChangeListener) list[i]).propertyChange(event);
			}
		}

	}

	static class ViewModel {
		private Person person = new Person();

		public Person getPerson() {
			return person;
		}
	}

	static class View {
		private ViewModel viewModel;
		private Text name;

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			shell.setLayout(new RowLayout(SWT.VERTICAL));
			name = new Text(shell, SWT.BORDER);

			DataBindingContext bindingContext = new DataBindingContext();
			Person person = viewModel.getPerson();

			IValueProperty nameProperty = JFaceProperties.value(Person.class,
					"name", NAME_PROPERTY);

			bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(name), nameProperty.observe(person));

			Label label = new Label(shell, SWT.NONE);
			bindingContext.bindValue(WidgetProperties.text().observe(label), nameProperty.observe(person));

			shell.pack();
			shell.open();
			return shell;
		}
	}

}
