/*******************************************************************************
 * Copyright (c) 2022 Jens Lidestrom and others
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jens Lidestrom - Initial API and implementation
 ******************************************************************************/
package org.eclipse.jface.examples.databinding.snippets;

import static org.eclipse.core.databinding.AggregateValidationStatus.MAX_SEVERITY;
import static org.eclipse.core.databinding.validation.ValidationStatus.error;
import static org.eclipse.core.databinding.validation.ValidationStatus.ok;

import java.util.Objects;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.Bind;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.TextFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Snippet that demonstrates the fluent databinding API based on the
 * {@link Bind} class.
 */
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

		LabelFactory.newLabel(SWT.NONE).text("Enter '5' to be valid:").create(shell);

		Text text = TextFactory.newText(SWT.BORDER).layoutData(GridDataFactory.fillDefaults().create()).create(shell);

		LabelFactory.newLabel(SWT.NONE).text("Error:").create(shell);

				.layoutData(GridDataFactory.fillDefaults().hint(200, SWT.DEFAULT).create()) //
				.create(shell);

		var value = new WritableValue<>(3, Integer.class);


		ButtonFactory.newButton(SWT.NONE).text("Set zero!").onSelect(e -> value.setValue(0)).create(shell);

		ButtonFactory.newButton(SWT.NONE).text("Set five!").onSelect(e -> value.setValue(5)).create(shell);

		var bindingContext = new DataBindingContext();


		IValidator<Integer> fiveValidator = v -> v == 5 ? ok() : error("Value was '" + v + "', not '5'");

				.validateTwoWay(v -> v.matches("\\s*\\d+\\s*") ? Status.OK_STATUS : error("Not a number: '" + v + "'"))
				.convertFrom(IConverter.create(i -> Objects.toString(i, ""))) //
				.bind(bindingContext);

				.validateAfterGet(fiveValidator)
				.bind(bindingContext);

				.from(new AggregateValidationStatus(bindingContext.getBindings(), MAX_SEVERITY)) //
				.bind(bindingContext);

		shell.pack();
		shell.open();

		return shell;
	}
}
