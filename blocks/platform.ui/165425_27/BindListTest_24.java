package org.eclipse.jface.examples.databinding.snippets;

import static org.eclipse.core.databinding.validation.ValidationStatus.error;
import static org.eclipse.core.databinding.validation.ValidationStatus.ok;

import java.util.Objects;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.Bind;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet043BindApi {
	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = createShell();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

	private static Shell createShell() {
		Shell shell = new Shell();
		shell.setText("Data Binding Snippet 004");
		shell.setLayout(new GridLayout(2, false));

		new Label(shell, SWT.NONE).setText("Enter '5' to be valid:");

		Text text = new Text(shell, SWT.BORDER);
		GridDataFactory.fillDefaults().applyTo(text);
		WritableValue<Integer> value = WritableValue.withValueType(Integer.class);
		value.setValue(3);
		new Label(shell, SWT.NONE).setText("Error:");

		Label errorLabel = new Label(shell, SWT.BORDER);
		errorLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(errorLabel);

		DataBindingContext bindingContext = new DataBindingContext();

		IValidator<Integer> fiveValidator = v -> v == 5 ? ok() : error("the value was '" + v + "', not '5'");

		Bind.twoWay() //
				.from(value) //
				.validateAfterConvert(fiveValidator) //
				.convertTo(IConverter.create(i -> Objects.toString(i, ""))) //
				.convertFrom(IConverter.create(s -> s.isEmpty() ? 0 : Integer.decode(s))) //
				.to(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateTwoWay(v -> v.matches("\\s*\\d+\\s*") ? Status.OK_STATUS
						: error("Not a number: '" + v + "'"))
				.bind(bindingContext);

		Bind.oneWay() //
				.from(value) //
				.validateAfterGet(fiveValidator) //
				.defaultConvert() //
				.to(WidgetProperties.text(SWT.Modify).observe(text)) //
				.convertOnly() //
				.bind(bindingContext);

		Bind.oneWay() //
				.from(new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY)) //
				.defaultConvert() //
				.to(WidgetProperties.text().observe(errorLabel)) //
				.bind(bindingContext);


		Button zeroButton = new Button(shell, SWT.NONE);
		zeroButton.setText("Set zero!");
		zeroButton.addListener(SWT.Selection, e -> value.setValue(0));

		Button fiveButton = new Button(shell, SWT.NONE);
		fiveButton.setText("Set five!");
		fiveButton.addListener(SWT.Selection, e -> value.setValue(5));

		shell.pack();
		shell.open();
		return shell;
	}
}
