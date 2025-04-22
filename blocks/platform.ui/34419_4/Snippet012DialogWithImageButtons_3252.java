
package org.eclipse.jface.examples.databinding.snippets;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet0xffffffff {
	public static void main(String[] args) {
		ViewModel viewModel = new ViewModel();
		Shell shell = new View(viewModel).createShell();

		Display display = Display.getCurrent();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		System.out.println("person.getName() = "
				+ viewModel.getPerson().getName());
	}

	public static abstract class AbstractModelObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
				this);

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(listener);
		}

		public void addPropertyChangeListener(String propertyName,
				PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName,
					listener);
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
	}

	static class Person extends AbstractModelObject {
		String name = "John Smith";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			final Display display = Display.getCurrent();
			Shell shell = new Shell(display);
			shell.setLayout(new RowLayout(SWT.VERTICAL));

			final Text name = new Text(shell, SWT.BORDER);

			name.setText(viewModel.getPerson().getName());
			name.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					final String text = name.getText();
					viewModel.getPerson().setName(text);
				}
			});
			viewModel.person.addPropertyChangeListener("name",
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							display.asyncExec(new Runnable() {
								@Override
								public void run() {
									final String newName = viewModel.person.getName();
									name.setText(newName);
								}
							});
						}
					});

			shell.pack();
			shell.open();
			return shell;
		}
	}

}
