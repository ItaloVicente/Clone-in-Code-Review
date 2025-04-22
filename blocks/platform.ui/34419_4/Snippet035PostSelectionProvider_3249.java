
package org.eclipse.jface.examples.databinding.snippets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet034ComboViewerAndEnum {

	public static void main(String[] args) {
		Display display = new Display();
		final Person model = new Person("Pat", Gender.Unknown);

		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final Shell shell = new View(model).createShell();
				Display display = Display.getCurrent();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		System.out.println("person.getName() = " + model.getName());
		System.out.println("person.getGender() = " + model.getGender());
	}

	static enum Gender {
		Male, Female, Unknown;
	}

	static class Person {
		String name;
		Gender gender;

		public Person(String name, Gender gender) {
			this.name = name;
			this.gender = gender;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender newGender) {
			this.gender = newGender;
		}
	}

	static class View {
		private Person viewModel;
		private Text name;
		private ComboViewer gender;

		public View(Person viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);

			RowLayout layout = new RowLayout(SWT.VERTICAL);
			layout.fill = true;
			layout.marginWidth = layout.marginHeight = 5;
			shell.setLayout(layout);

			name = new Text(shell, SWT.BORDER);
			gender = new ComboViewer(shell, SWT.READ_ONLY);

			gender.setContentProvider(ArrayContentProvider.getInstance());
			gender.setInput(Gender.values());

			DataBindingContext bindingContext = new DataBindingContext();

			IObservableValue widgetObservable = WidgetProperties.text(SWT.Modify).observe(
					name);
			bindingContext.bindValue(widgetObservable,
					PojoProperties.value(viewModel.getClass(), "name")
								.observe(viewModel));

			widgetObservable = ViewersObservables
					.observeSingleSelection(gender);
			bindingContext.bindValue(widgetObservable,
					PojoProperties.value(viewModel.getClass(), "gender")
								.observe(viewModel));

			shell.pack();
			shell.open();
			return shell;
		}
	}

}
