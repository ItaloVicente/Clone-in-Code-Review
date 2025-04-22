
package org.eclipse.jface.examples.databinding.snippets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Snippet035PostSelectionProvider {

	private DataBindingContext dbc;

	private ListViewer listViewer;

	public static void main(String[] args) {
		Display display = new Display();

		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Snippet035PostSelectionProvider()
						.createShell();
				Display display = Display.getCurrent();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
	}

	private Shell createShell() {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setText("Post Selections");
		shell.setLayout(new GridLayout(1, false));

		dbc = new DataBindingContext();

		createTableSection(shell);
		createFieldSection(shell);

		shell.pack();
		shell.open();

		return shell;
	}

	private void createTableSection(Composite parent) {
		Group section = createSectionGroup(parent, 1);

		listViewer = new ListViewer(section, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).hint(250, 250).applyTo(
				listViewer.getList());

		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new LabelProvider());

		String[] names = new String[] { "John Doe", "Steve Northover",
				"Grant Gayed", "Veronika Irvine", "Mike Wilson",
				"Christophe Cornu", "Lynne Kues", "Silenio Quarti" };

		listViewer.setInput(names);
	}

	private void createFieldSection(Composite parent) {
		final Group section = createSectionGroup(parent, 2);

		Label selectionLabel = createLabelField(section, "Selection:");
		IViewerObservableValue selectionObservable = ViewerProperties
				.singleSelection().observe(listViewer);
		dbc.bindValue(WidgetProperties.text().observe(selectionLabel),
				selectionObservable);

		Label postSelectionLabel = createLabelField(section, "Post selection:");
		IViewerObservableValue postSelectionObservable = ViewerProperties
				.singlePostSelection().observe(listViewer);
		dbc.bindValue(WidgetProperties.text().observe(postSelectionLabel),
				postSelectionObservable);
	}

	private Group createSectionGroup(Composite parent, int numColumns) {
		Group section = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayoutFactory.fillDefaults().numColumns(numColumns).equalWidth(
				false).margins(5, 5).spacing(15, 5).applyTo(section);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		return section;
	}

	private static Label createLabelField(Composite parent, String labelText) {
		Label nameLabel = new Label(parent, SWT.LEFT);
		nameLabel.setText(labelText);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(
				nameLabel);

		Label contentLabel = new Label(parent, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).hint(150, SWT.DEFAULT)
				.applyTo(contentLabel);

		return contentLabel;
	}
}
