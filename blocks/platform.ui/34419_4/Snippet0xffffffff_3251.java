
package org.eclipse.jface.examples.databinding.snippets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.dialog.IValidationMessageProvider;
import org.eclipse.jface.databinding.dialog.ValidationMessageProvider;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationUpdater;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Snippet036ValidationMessageProvider {

	public static void main(String[] args) {
		Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				IWizard wizard = new MessageProviderWizard();
				WizardDialog wizardDialog = new WizardDialog(null, wizard);
				wizardDialog.open();

				Display display = Display.getCurrent();
				while (wizardDialog.getShell() != null
						&& !wizardDialog.getShell().isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
	}

	private static final class MessageProviderWizard extends Wizard {

		@Override
		public void addPages() {
			addPage(new MessageProviderWizardPage());
		}

		@Override
		public String getWindowTitle() {
			return "Snippet 036 - IValidationMessageProvider";
		}

		@Override
		public boolean performFinish() {
			return true;
		}
	}

	private static final class MessageProviderWizardPage extends WizardPage {

		private DataBindingContext dbc;
		private Map bindingMapName;

		protected MessageProviderWizardPage() {
			super(MessageProviderWizardPage.class.getName());
			setTitle("Snippet 036 - IValidationMessageProvider");
			setDescription("Please fill in the form.");
		}

		@Override
		public void createControl(Composite parent) {
			dbc = new DataBindingContext();
			bindingMapName = new HashMap();

			Composite container = new Composite(parent, SWT.NULL);
			GridLayoutFactory.fillDefaults().numColumns(2).margins(5, 5)
					.spacing(15, 5).applyTo(container);
			setControl(container);

			createTextLine(container, "Name", WritableValue
					.withValueType(String.class));
			createTextLine(container, "Age", WritableValue
					.withValueType(Integer.class));
			createTextLine(container, "Birthday", WritableValue
					.withValueType(Date.class));

			WizardPageSupport wps = WizardPageSupport.create(this, dbc);

			wps.setValidationMessageProvider(new CustomMessageProvider(
					bindingMapName));
		}

		private void createTextLine(Composite parent, String labelText,
				IObservableValue modelValue) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(labelText);
			GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(
					label);

			final Text text = new Text(parent, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(text);

			ISWTObservableValue textObservable = WidgetProperties.text(SWT.Modify).observe(text);

			Binding binding = dbc.bindValue(textObservable, modelValue,
					new UpdateValueStrategy()
							.setAfterConvertValidator(new RequiredValidator()),
					new UpdateValueStrategy());

			ControlDecorationUpdater decorationUpdater = new ControlDecorationUpdater() {

				@Override
				protected Image getImage(IStatus status) {
					if (text.getText().length() == 0) {
						FieldDecoration fieldDecoration = FieldDecorationRegistry
								.getDefault().getFieldDecoration(
										FieldDecorationRegistry.DEC_REQUIRED);
						return fieldDecoration.getImage();
					}
					return super.getImage(status);
				}
			};

			ControlDecorationSupport.create(binding, SWT.TOP, null,
					decorationUpdater);

			bindingMapName.put(binding, labelText);
		}
	}

	private static final class CustomMessageProvider extends
			ValidationMessageProvider {

		private final Map bindingMapName;

		public CustomMessageProvider(Map bindingMapName) {
			this.bindingMapName = bindingMapName;
		}

		@Override
		public String getMessage(ValidationStatusProvider statusProvider) {
			if (statusProvider != null) {
				String name = (String) bindingMapName.get(statusProvider);
				if (name != null) {
					String message = super.getMessage(statusProvider);
					return name + ": " + message;
				}
			}
			return super.getMessage(statusProvider);
		}

		@Override
		public int getMessageType(ValidationStatusProvider statusProvider) {
			if (statusProvider instanceof Binding) {
				Binding binding = (Binding) statusProvider;
				IStatus status = (IStatus) binding.getValidationStatus()
						.getValue();

				if (status.matches(IStatus.ERROR)) {
					IObservableValue target = (IObservableValue) binding
							.getTarget();
					if ("".equals(target.getValue())) {
						return IMessageProvider.NONE;
					}
				}
			}
			return super.getMessageType(statusProvider);
		}
	}

	private static final class RequiredValidator implements IValidator {

		@Override
		public IStatus validate(Object value) {
			if (value == null || "".equals(value)) {
				return ValidationStatus.error("Please specify a value.");
			}
			return ValidationStatus.ok();
		}
	}
}
