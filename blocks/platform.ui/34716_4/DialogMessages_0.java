package org.eclipse.e4.ui.dialogs;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.e4.ui.dialogs.textbundles.DialogMessages;
import org.eclipse.jface.databinding.viewers.IViewerObservableSet;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.SelectionDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ListSelectionDialog<T> extends SelectionDialog<T> {

	private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

	private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;
	private Object inputElement;

	private ILabelProvider labelProvider;

	private IStructuredContentProvider contentProvider;

	private CheckboxTableViewer viewer;

	private Class<T> elementType;

	private DataBindingContext dbc;

	public static <T> SelectionDialog<T> create(Shell parentShell,
			Collection<String> input, Class<T> elementType) {

		return new ListSelectionDialog<T>(parentShell, input, elementType,
				ArrayContentProvider.getInstance(), new LabelProvider(),
				DialogMessages.ListSelection_title,
				DialogMessages.ListSelection_message);
	}

	public static <T> SelectionDialog<T> create(Shell parentShell,
			Object input, Class<T> elementType,
			IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider) {

		return new ListSelectionDialog<T>(parentShell, input, elementType,
				contentProvider, labelProvider,
				DialogMessages.ListSelection_title,
				DialogMessages.ListSelection_message);
	}

	public static <T> SelectionDialog<T> create(Shell parentShell,
			Object input, Class<T> elementType,
			IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String title, String message) {

		return new ListSelectionDialog<T>(parentShell, input, elementType,
				contentProvider, labelProvider, title != null ? title
						: DialogMessages.ListSelection_title,
				message != null ? message
						: DialogMessages.ListSelection_message);
	}

	@Override
	public boolean close() {
		if (dbc != null) {
			dbc.dispose();
		}
		return super.close();
	}

	protected ListSelectionDialog(Shell parentShell, Object input,
			Class<T> elementType, IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String title, String message) {
		super(parentShell);
		this.elementType = elementType;
		this.inputElement = input;
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
		setTitle(title);
		setMessage(message);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		dbc = new DataBindingContext();

		initializeDialogUnits(composite);

		createMessageArea(composite);

		viewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
		GridDataFactory
				.swtDefaults()
				.hint(SIZING_SELECTION_WIDGET_WIDTH,
						SIZING_SELECTION_WIDGET_HEIGHT)
				.applyTo(viewer.getControl());

		viewer.setLabelProvider(labelProvider);
		viewer.setContentProvider(contentProvider);

		IViewerObservableValue viewerInputObservable = ViewerProperties.input()
				.observe(viewer);
		dbc.bindValue(viewerInputObservable, new WritableValue(inputElement,
				null));

		Collection<T> initialElementSelections = getInitialSelection();
		IObservableSet resultSet = Properties.selfSet(elementType).observe(
				initialElementSelections);
		setResult(resultSet);

		IViewerObservableSet viewerCheckedElementsObservable = ViewerProperties
				.checkedElements(elementType).observe(viewer);
		dbc.bindSet(viewerCheckedElementsObservable, resultSet);

		addSelectionButtons(composite);

		Dialog.applyDialogFont(composite);

		return composite;
	}

	private void addSelectionButtons(Composite parent) {
		Composite buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		buttonComposite.setLayout(layout);
		buttonComposite.setLayoutData(new GridData(SWT.END, SWT.TOP, true,
				false));

		Button selectButton = createButton(buttonComposite,
				IDialogConstants.SELECT_ALL_ID,
				DialogMessages.SelectionDialog_selectLabel, false);

		selectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] elements = contentProvider.getElements(inputElement);
				getResult().addAll(
						(Collection<? extends T>) Arrays.asList(elements));
			}
		});

		Button deselectButton = createButton(buttonComposite,
				IDialogConstants.DESELECT_ALL_ID,
				DialogMessages.SelectionDialog_deselectLabel, false);

		deselectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getResult().clear();
			}
		});
	}

}
