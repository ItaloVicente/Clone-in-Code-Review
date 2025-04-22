/*******************************************************************************
 * Copyright (c) 2006, 2018 The Pampered Chef, Inc. and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     The Pampered Chef, Inc. - initial API and implementation
 *     Brad Reynolds - bug 116920
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Snippet -1.
 *
 * Hello, no databinding. Bind changes in a GUI to a Model object but don't
 * worry about propogating changes from the Model to the GUI -- using *manual*
 * code. (0xffffffff is -1 in 32-bit two's complement binary arithmatic)
 */
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
			name.addModifyListener(e -> {
				final String text = name.getText();
				viewModel.getPerson().setName(text);
			});
			viewModel.person.addPropertyChangeListener("name",
					evt -> display.asyncExec(() -> {
						final String newName = viewModel.person.getName();
						name.setText(newName);
					}));

			shell.pack();
			shell.open();
			return shell;
		}
	}

}
