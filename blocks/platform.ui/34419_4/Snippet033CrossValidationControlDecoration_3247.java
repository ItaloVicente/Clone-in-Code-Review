
package org.eclipse.jface.examples.databinding.snippets;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class Snippet032TableViewerColumnEditing {
	public static void main(String[] args) {
		final Display display = new Display();
		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				ViewModel viewModel = new ViewModel();
				Shell shell = new View(viewModel).createShell();

				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
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
		String name;
		String firstName;

		public Person(String firstName, String name) {
			this.name = name;
			this.firstName = firstName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			firePropertyChange("name", this.name, this.name = name);
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			firePropertyChange("firstName", this.firstName,
					this.firstName = firstName);
		}
	}

	static class ViewModel {
		private List people = new LinkedList();
		{
			people.add(new Person("Dave", "Orme"));
			people.add(new Person("Gili", "Mendel"));
			people.add(new Person("Joe", "Winchester"));
			people.add(new Person("Boris", "Bokowski"));
			people.add(new Person("Brad", "Reynolds"));
			people.add(new Person("Matthew", "Hall"));
		}

		public List getPeople() {
			return people;
		}
	}

	static class View {
		private ViewModel viewModel;
		private Table committers;
		private Label selectedCommitterName;
		private Label selectedCommitterFirstName;

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			shell.setLayout(new GridLayout(2, true));
			committers = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
			committers.setLinesVisible(true);
			committers.setHeaderVisible(true);
			GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
			layoutData.horizontalSpan = 2;
			committers.setLayoutData(layoutData);

			GridData fieldLayoutData = new GridData(SWT.FILL, SWT.BEGINNING,
					true, false);
			selectedCommitterName = new Label(shell, SWT.NONE);
			selectedCommitterName.setLayoutData(fieldLayoutData);

			selectedCommitterFirstName = new Label(shell, SWT.NONE);
			selectedCommitterFirstName.setLayoutData(fieldLayoutData);

			DataBindingContext bindingContext = new DataBindingContext();
			bindGUI(bindingContext);

			shell.setSize(250, 300);
			shell.open();
			return shell;
		}

		protected void bindGUI(DataBindingContext bindingContext) {
			TableViewer peopleViewer = new TableViewer(committers);

			TableViewerColumn columnName = new TableViewerColumn(peopleViewer,
					SWT.NONE);
			columnName.getColumn().setText("Name");
			columnName.getColumn().setWidth(100);

			TableViewerColumn columnFirstName = new TableViewerColumn(
					peopleViewer, SWT.NONE);
			columnFirstName.getColumn().setText("FirstName");
			columnFirstName.getColumn().setWidth(100);

			IBeanValueProperty propName = BeanProperties.value(Person.class,
					"name");
			IBeanValueProperty propFirstname = BeanProperties.value(
					Person.class, "firstName");

			IValueProperty cellEditorControlText = CellEditorProperties
					.control().value(WidgetProperties.text());

			columnName.setEditingSupport(ObservableValueEditingSupport.create(
					peopleViewer, bindingContext,
					new TextCellEditor(committers), cellEditorControlText,
					propName));
			columnFirstName.setEditingSupport(ObservableValueEditingSupport
					.create(peopleViewer, bindingContext, new TextCellEditor(
							committers), cellEditorControlText, propFirstname));

			ObservableListContentProvider contentProvider = new ObservableListContentProvider();
			peopleViewer.setContentProvider(contentProvider);

			IObservableMap[] result = Properties.observeEach(contentProvider
					.getKnownElements(), new IBeanValueProperty[] { propName,
					propFirstname });

			columnName.setLabelProvider(new ObservableMapCellLabelProvider(
					result[0]));
			columnFirstName
					.setLabelProvider(new ObservableMapCellLabelProvider(
							result[1]));

			peopleViewer.setInput(new WritableList(viewModel.getPeople(),
					Person.class));

			IObservableValue selection = ViewersObservables
					.observeSingleSelection(peopleViewer);
			bindingContext.bindValue(
					WidgetProperties.text().observe(selectedCommitterName),
					BeanProperties.value((Class) selection.getValueType(), "name", String.class)
					.observeDetail(selection));
			bindingContext.bindValue(
					WidgetProperties.text().observe(selectedCommitterFirstName),
					BeanProperties.value((Class) selection.getValueType(), "firstName", String.class)
					.observeDetail(selection));
		}
	}
}
