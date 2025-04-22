
package org.eclipse.jface.examples.databinding.snippets;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class Snippet013TableViewerEditing {
	public static void main(String[] args) {
		final Display display = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
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
		String name = "John Smith";

		public Person(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			String oldValue = this.name;
			this.name = name;
			firePropertyChange("name", oldValue, name);
		}
	}

	static class ViewModel {
		private List people = new LinkedList();
		{
			people.add(new Person("Steve Northover"));
			people.add(new Person("Grant Gayed"));
			people.add(new Person("Veronika Irvine"));
			people.add(new Person("Mike Wilson"));
			people.add(new Person("Christophe Cornu"));
			people.add(new Person("Lynne Kues"));
			people.add(new Person("Silenio Quarti"));
		}

		public List getPeople() {
			return people;
		}
	}

	private static class InlineEditingSupport extends
			ObservableValueEditingSupport {
		private CellEditor cellEditor;

		public InlineEditingSupport(ColumnViewer viewer, DataBindingContext dbc) {

			super(viewer, dbc);
			cellEditor = new TextCellEditor((Composite) viewer.getControl());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected IObservableValue doCreateCellEditorObservable(
				CellEditor cellEditor) {

			return SWTObservables.observeText(cellEditor.getControl(),
					SWT.Modify);
		}

		@Override
		protected IObservableValue doCreateElementObservable(Object element,
				ViewerCell cell) {
			return BeanProperties.value(element.getClass(), "name").observe(element);
		}
	}

	static class View {
		private ViewModel viewModel;
		private Table committers;
		private Label selectedCommitter;

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			shell.setLayout(new FillLayout(SWT.VERTICAL));
			committers = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
			committers.setLinesVisible(true);

			selectedCommitter = new Label(shell, SWT.NONE);
			DataBindingContext bindingContext = new DataBindingContext();
			bindGUI(bindingContext);

			shell.setSize(100, 300);
			shell.open();
			return shell;
		}

		protected void bindGUI(DataBindingContext bindingContext) {
			TableViewer peopleViewer = new TableViewer(committers);
			TableViewerColumn column = new TableViewerColumn(peopleViewer,
					SWT.NONE);
			column.setEditingSupport(new InlineEditingSupport(peopleViewer,
					bindingContext));
			column.getColumn().setWidth(100);

			ViewerSupport.bind(peopleViewer, new WritableList(viewModel
					.getPeople(), Person.class), BeanProperties.value(
					Person.class, "name"));

			IObservableValue selection = ViewersObservables
					.observeSingleSelection(peopleViewer);
			bindingContext.bindValue(WidgetProperties.text().observe(selectedCommitter),
					BeanProperties.value((Class) selection.getValueType(), "name", String.class)
					.observeDetail(selection));
		}
	}

}
