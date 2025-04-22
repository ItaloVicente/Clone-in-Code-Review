package org.eclipse.jface.examples.databinding.contentprovider.test;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.MappedSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.internal.databinding.provisional.swt.ControlUpdater;
import org.eclipse.jface.internal.databinding.provisional.viewers.ViewerLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class StructuredContentProviderTest {

	private static Realm realm;

	private Shell shell;

	protected Random random = new Random();


	private WritableSet inputSet;

	private WritableValue currentFunction;

	private SomeMathFunction mathFunction;

	private MappedSet outputSet;

	private IObservableValue sumOfOutputSet;

	public StructuredContentProviderTest() {

		createDataModel();

		shell = new Shell(Display.getCurrent(), SWT.SHELL_TRIM);
		{ // Initialize shell
			final Label someDoubles = new Label(shell, SWT.NONE);
			someDoubles.setText("A list of random Doubles"); //$NON-NLS-1$
			someDoubles.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_FILL
							| GridData.VERTICAL_ALIGN_FILL));

			Control addRemoveComposite = createInputControl(shell, inputSet);

			GridData addRemoveData = new GridData(GridData.FILL_BOTH);
			addRemoveData.minimumHeight = 1;
			addRemoveData.minimumWidth = 1;

			addRemoveComposite.setLayoutData(addRemoveData);

			Group operation = new Group(shell, SWT.NONE);
			{ // Initialize operation group
				operation.setText("Select transformation"); //$NON-NLS-1$

				createRadioButton(operation, currentFunction, "f(x) = x", //$NON-NLS-1$
						new Integer(SomeMathFunction.OP_IDENTITY));
				createRadioButton(operation, currentFunction, "f(x) = 2 * x", //$NON-NLS-1$
						new Integer(SomeMathFunction.OP_MULTIPLY));
				createRadioButton(operation, currentFunction,
						"f(x) = floor(x)", new Integer( //$NON-NLS-1$
								SomeMathFunction.OP_ROUND));

				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				operation.setLayout(layout);
			}
			operation.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_FILL));

			Control outputControl = createOutputComposite(shell);
			GridData outputData = new GridData(GridData.FILL_BOTH);
			outputData.minimumHeight = 1;
			outputData.minimumWidth = 1;
			outputData.widthHint = 300;
			outputData.heightHint = 150;

			outputControl.setLayoutData(outputData);

			final Label sumLabel = new Label(shell, SWT.NONE);
			new ControlUpdater(sumLabel) {
				@Override
				protected void updateControl() {
					double sum = ((Double) sumOfOutputSet.getValue())
							.doubleValue();
					int size = outputSet.size();

					sumLabel.setText("The sum of the above " + size //$NON-NLS-1$
							+ " doubles is " + sum); //$NON-NLS-1$
				}
			};
			sumLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_FILL));

			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			shell.setLayout(layout);
		}

	}

	private void createDataModel() {

		inputSet = new WritableSet(realm);

		currentFunction = new WritableValue(realm, new Integer(
				SomeMathFunction.OP_MULTIPLY), null);

		mathFunction = new SomeMathFunction(inputSet);
		currentFunction.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				mathFunction
						.setOperation(((Integer) currentFunction.getValue())
								.intValue());
			}
		});
		mathFunction.setOperation(((Integer) currentFunction.getValue())
				.intValue());

		outputSet = new MappedSet(inputSet, mathFunction);

		sumOfOutputSet = new ComputedValue(realm) {
			@Override
			protected Object calculate() {
				double sum = 0.0;
				for (Iterator iter = outputSet.iterator(); iter.hasNext();) {
					Double next = (Double) iter.next();

					sum += next.doubleValue();
				}
				return new Double(sum);
			}
		};
	}

	private void createRadioButton(Composite parent, final WritableValue model,
			String string, final Object value) {
		final Button button = new Button(parent, SWT.RADIO);
		button.setText(string);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setValue(value);
				super.widgetSelected(e);
			}
		});
		new ControlUpdater(button) {
			@Override
			protected void updateControl() {
				button.setSelection(model.getValue().equals(value));
			}
		};
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));
	}

	private Control createOutputComposite(Composite parent) {
		ListViewer listOfInts = new ListViewer(parent, SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);

		listOfInts.setContentProvider(new ObservableSetContentProvider());
		listOfInts.setLabelProvider(new ViewerLabelProvider());
		listOfInts.setInput(outputSet);
		return listOfInts.getControl();
	}

	private Control createInputControl(Composite parent,
			final WritableSet inputSet) {
		Composite addRemoveComposite = new Composite(parent, SWT.NONE);
		{ // Initialize addRemoveComposite
			ListViewer listOfInts = new ListViewer(addRemoveComposite,
					SWT.BORDER);

			listOfInts.setContentProvider(new ObservableSetContentProvider());
			listOfInts.setLabelProvider(new ViewerLabelProvider());
			listOfInts.setInput(inputSet);

			final IObservableValue selectedInt = ViewersObservables.observeSingleSelection(listOfInts);

			GridData listData = new GridData(GridData.FILL_BOTH);
			listData.minimumHeight = 1;
			listData.minimumWidth = 1;
			listData.widthHint = 150;
			listData.heightHint = 150;
			listOfInts.getControl().setLayoutData(listData);

			Composite buttonBar = new Composite(addRemoveComposite, SWT.NONE);
			{ // Initialize button bar

				Button add = new Button(buttonBar, SWT.PUSH);
				add.setText("Add"); //$NON-NLS-1$
				add.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						inputSet.add(new Double(random.nextDouble() * 100.0));
						super.widgetSelected(e);
					}
				});
				add.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_FILL));

				final Button remove = new Button(buttonBar, SWT.PUSH);
				remove.setText("Remove"); //$NON-NLS-1$
				new ControlUpdater(remove) {
					@Override
					protected void updateControl() {

						remove.setEnabled(selectedInt.getValue() != null);
					}
				};

				remove.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						inputSet.remove(selectedInt.getValue());
						super.widgetSelected(e);
					}
				});
				remove.setLayoutData(new GridData(
						GridData.HORIZONTAL_ALIGN_FILL
								| GridData.VERTICAL_ALIGN_FILL));

				GridLayout buttonLayout = new GridLayout();
				buttonLayout.numColumns = 1;
				buttonBar.setLayout(buttonLayout);

			} // End button bar
			buttonBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_BEGINNING));

			GridLayout addRemoveLayout = new GridLayout();
			addRemoveLayout.numColumns = 2;
			addRemoveComposite.setLayout(addRemoveLayout);
		}
		return addRemoveComposite;
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		realm = DisplayRealm.getRealm(display);
		StructuredContentProviderTest test = new StructuredContentProviderTest();
		Shell s = test.getShell();
		s.pack();
		s.setVisible(true);

		while (!s.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private Shell getShell() {
		return shell;
	}

}
