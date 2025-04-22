package org.eclipse.jface.tests.databinding.scenarios;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.conversion.NumberToStringConverter;
import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.internal.databinding.conversion.IdentityConverter;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Account;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.Cart;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.NumberFormat;


public class PropertyScenarios extends ScenariosTestCase {

    private Adventure adventure;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        adventure = SampleData.WINTER_HOLIDAY;
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testEnterText() {
        Text text = new Text(getComposite(), SWT.BORDER);
        final boolean[] focusLostHolder = { false };
        text.addFocusListener(new FocusListener() {

            @Override
			public void focusGained(FocusEvent e) {
            }

            @Override
			public void focusLost(FocusEvent e) {
                focusLostHolder[0] = true;
            }
        });
        enterText(text, "hallo");
        assertTrue(focusLostHolder[0]);
    }

    public void testScenario01() {
        Text text = new Text(getComposite(), SWT.BORDER);
        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(adventure, "name"));

        assertEquals(adventure.getName(), text.getText());
        enterText(text, "foobar");
        assertEquals("foobar", adventure.getName());
        adventure.setName("barfoo");
        assertEquals("barfoo", text.getText());
    }

    public void testScenario02() {
        Text text = new Text(getComposite(), SWT.READ_ONLY);

        getDbc().bindValue(SWTObservables.observeText(text, SWT.None),
                BeansObservables.observeValue(adventure, "name"));
        assertEquals(adventure.getName(), text.getText());
    }

    public void testScenario03() {
        Cart cart = SampleData.CART;
        cart.setAdventureDays(42);
        Text text = new Text(getComposite(), SWT.BORDER);

        System.out.println("Expecting message about not being able to attach a listener");
        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(cart, "lodgingDays"));

        assertEquals(new Integer(cart.getLodgingDays()).toString(), text.getText());
    }

    public void testScenario04() {
        Text text = new Text(getComposite(), SWT.BORDER);

        IObservableValue defaultLodging = BeansObservables.observeDetailValue(
        		BeansObservables.observeValue(adventure, "defaultLodging"),
        		"description", String.class); 

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), defaultLodging);

        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        enterText(text, "foobar");
        assertEquals("foobar", adventure.getDefaultLodging().getDescription());
        adventure.getDefaultLodging().setDescription("barfoo");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

        adventure.setDefaultLodging(SampleData.CAMP_GROUND);
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        adventure.getDefaultLodging().setDescription("barfo");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

        adventure.setDefaultLodging(null);
        assertEquals("", text.getText());

        adventure.setDefaultLodging(SampleData.FIVE_STAR_HOTEL);
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        adventure.getDefaultLodging().setDescription("barf");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

    }

    public void testScenario05() {
        Text text = new Text(getComposite(), SWT.BORDER);
        adventure.setName("UPPERCASE");

        IConverter converter1 = new IConverter() {
            @Override
			public Object getFromType() {
                return String.class;
            }

            @Override
			public Object getToType() {
                return String.class;
            }

            @Override
			public Object convert(Object toObject) {
                String modelValue = (String) toObject;
                if (modelValue == null || modelValue.equals("")) {
                    return modelValue;
                }
                String firstChar = modelValue.substring(0, 1);
                String remainingChars = modelValue.substring(1);
                return firstChar.toUpperCase() + remainingChars.toLowerCase();
            }
        };
        IConverter converter2 = new IConverter() {
            @Override
			public Object getFromType() {
                return String.class;
            }

            @Override
			public Object getToType() {
                return String.class;
            }

            @Override
			public Object convert(Object fromObject) {
                return ((String) fromObject).toUpperCase();
            }
        };

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(adventure, "name"),
                new UpdateValueStrategy().setConverter(converter2), new UpdateValueStrategy().setConverter(converter1));

        assertEquals("Uppercase", text.getText());
        enterText(text, "lowercase");
        assertEquals("LOWERCASE", adventure.getName());
    }

    public void testScenario06() {
        Text text = new Text(getComposite(), SWT.BORDER);
        final String noSpacesMessage = "Name must not contain spaces.";
        final String max15CharactersMessage = "Maximum length for name is 15 characters.";
        adventure.setName("ValidValue");

        IValidator validator = new IValidator() {
            @Override
			public IStatus validate(Object value) {
                String stringValue = (String) value;
                if (stringValue.length() > 15) {
                    return ValidationStatus.error(max15CharactersMessage);
                } else if (stringValue.indexOf(' ') != -1) {
                    return ValidationStatus.cancel(noSpacesMessage);
                } else {
                    return Status.OK_STATUS;
                }
            }
        };


        Binding binding = getDbc().bindValue(
				SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "name"),
				new UpdateValueStrategy().setConverter(new IdentityConverter(
						String.class)).setAfterGetValidator(validator),
				new UpdateValueStrategy().setConverter(new IdentityConverter(
						String.class)));

        assertTrue(((IStatus)binding.getValidationStatus().getValue()).isOK());
        enterText(text, "Invalid Value");
        assertEquals(noSpacesMessage, ((IStatus) binding.getValidationStatus().getValue()).getMessage());
        assertEquals("ValidValue", adventure.getName());
        text.setText("InvalidValueBecauseTooLong");
        assertEquals(max15CharactersMessage,
                ((IStatus) binding.getValidationStatus().getValue()).getMessage());
        assertEquals("ValidValue", adventure.getName());
        enterText(text, "anothervalid");
        assertTrue(((IStatus)binding.getValidationStatus().getValue()).isOK());
        assertEquals("anothervalid", adventure.getName());
    }

    public void testScenario07() {
        Text text = new Text(getComposite(), SWT.BORDER);
        adventure.setPrice(5.0);
        final String cannotBeNegativeMessage = "Price cannot be negative.";
        final String mustBeCurrencyMessage = "Price must be a currency.";

        IValidator validator = new IValidator() {
            @Override
			public IStatus validate(Object value) {
                String stringValue = (String) value;
                try {
                    double doubleValue = new Double(stringValue).doubleValue();
                    if (doubleValue < 0.0) {
                        return ValidationStatus.error(cannotBeNegativeMessage);
                    }
                    return Status.OK_STATUS;
                } catch (NumberFormatException ex) {
                    return ValidationStatus.error(mustBeCurrencyMessage);
                }
            }
        };
        
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(1);

		IConverter targetToModelConverter = StringToNumberConverter.toDouble(
				numberFormat, true);
		IConverter modelToTargetConverter = NumberToStringConverter.fromDouble(
				numberFormat, true);

		getDbc().bindValue(
				SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "price"),
				new UpdateValueStrategy().setAfterGetValidator(validator)
						.setConverter(targetToModelConverter),
				new UpdateValueStrategy().setConverter(modelToTargetConverter));

		String expected = numberFormat.format(adventure.getPrice());
        assertEquals(expected, text.getText());
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        
        String toEnter = numberFormat.format(0.65);
        enterText(text, toEnter);
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        assertEquals(0.65, adventure.getPrice(), 0.0001);
        
        adventure.setPrice(42.24);
        expected = numberFormat.format(adventure.getPrice());
        assertEquals(expected, text.getText());
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        
        enterText(text, "jygt");
        assertEquals(mustBeCurrencyMessage, AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).getMessage());
        
        toEnter = numberFormat.format(-23.9);
        enterText(text, toEnter);
        assertEquals(cannotBeNegativeMessage, AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).getMessage());
        assertEquals(42.24, adventure.getPrice(), 0.0001);
        
        adventure.setPrice(0.0);
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
    }

    public void testScenario08() {
        Text text = new Text(getComposite(), SWT.BORDER);
        adventure.setPrice(5.0);
        final String cannotBeNegativeMessage = "Price cannot be negative.";
        final String mustBeCurrencyMessage = "Price must be a currency.";
        final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

        IConverter toCurrency = new Converter(double.class, String.class) {
            @Override
			public Object convert(Object toObject) {
                return currencyFormat.format(((Double) toObject).doubleValue());
            }
        };

        IConverter toDouble = new Converter(String.class, double.class) {
            @Override
			public Object convert(Object fromObject) {
                try {
                    return new Double(currencyFormat.parse((String) fromObject).doubleValue());
                } catch (ParseException e) {
                    return new Double(0);
                }
            }
        };

        IValidator validator = new IValidator() {
            @Override
			public IStatus validate(Object value) {
                String stringValue = (String) value;
                try {
                    double doubleValue = currencyFormat.parse(stringValue).doubleValue();
                    if (doubleValue < 0.0) {
                        return ValidationStatus.error(cannotBeNegativeMessage);
                    }
                    return Status.OK_STATUS;
                } catch (ParseException e) {
                    return ValidationStatus.error(mustBeCurrencyMessage);
                }
            }
        };

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(adventure, "price"),
new UpdateValueStrategy().setConverter(toDouble).setAfterGetValidator(validator),new UpdateValueStrategy().setConverter(toCurrency));

        String expected = currencyFormat.format(5);
        assertEquals(expected, text.getText());
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        
        String toEnter = currencyFormat.format(0.65);
        enterText(text, toEnter);
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        assertEquals(0.65, adventure.getPrice(), 0.0001);
        
        adventure.setPrice(42.24);
        expected = currencyFormat.format(adventure.getPrice());
        assertEquals(expected, text.getText());
        
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
        enterText(text, "jygt");
        assertEquals(mustBeCurrencyMessage, AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).getMessage());
        
        toEnter = currencyFormat.format(-23.9);
        enterText(text, toEnter);
        
        assertEquals(cannotBeNegativeMessage, AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).getMessage());
        assertEquals(42.24, adventure.getPrice(), 0.0001);
        adventure.setPrice(0.0);
        assertTrue(AggregateValidationStatus.getStatusMaxSeverity(getDbc().getBindings()).isOK());
    }

    public void testScenario09() {
        Button checkbox = new Button(getComposite(), SWT.CHECK);
        adventure.setPetsAllowed(true);

        getDbc().bindValue(SWTObservables.observeSelection(checkbox),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(true, checkbox.getSelection());
        setButtonSelectionWithEvents(checkbox, false);
        assertEquals(false, adventure.isPetsAllowed());
        adventure.setPetsAllowed(true);
        assertEquals(true, checkbox.getSelection());
    }

    public void testScenario10() {
    }

    public void testScenario11() {
        Spinner spinner1 = new Spinner(getComposite(), SWT.NONE);
        spinner1.setSelection(10);
        spinner1.setMinimum(1);
        spinner1.setMaximum(100);
        Spinner spinner2 = new Spinner(getComposite(), SWT.NONE);
        spinner2.setMaximum(1);

        getDbc().bindValue(SWTObservables.observeSelection(spinner1), SWTObservables.observeMax(spinner2));

        assertEquals(1, spinner1.getSelection());
        spinner1.setSelection(10);
        spinner1.notifyListeners(SWT.Modify, new Event());
        assertEquals(10, spinner2.getMaximum());
    }

    public void testScenario12() {
        Button checkbox1 = new Button(getComposite(), SWT.CHECK);
        checkbox1.setSelection(false);
        Button checkbox2 = new Button(getComposite(), SWT.CHECK);
        checkbox2.setSelection(false);
        Text text1 = new Text(getComposite(), SWT.NONE);
        Text text2 = new Text(getComposite(), SWT.NONE);

        IObservableValue checkbox1Selected = SWTObservables.observeSelection(checkbox1);
        IObservableValue checkbox2Selected = SWTObservables.observeSelection(checkbox2);

        Converter negatingConverter = new Converter(boolean.class, boolean.class) {
            private Boolean negated(Boolean booleanObject) {
                return new Boolean(!booleanObject.booleanValue());
            }

            @Override
			public Object convert(Object targetObject) {
                return negated((Boolean) targetObject);
            }
        };

        getDbc().bindValue(checkbox1Selected,
                checkbox2Selected,new UpdateValueStrategy().setConverter(negatingConverter),
                new UpdateValueStrategy().setConverter(negatingConverter));
        
        
        getDbc().bindValue(SWTObservables.observeEnabled(text1), checkbox1Selected);
        getDbc().bindValue(SWTObservables.observeEnabled(text2), checkbox2Selected);
                
        assertEquals(true, text1.getEnabled());
        assertEquals(false, text2.getEnabled());
        assertEquals(true, checkbox1.getSelection());
        setButtonSelectionWithEvents(checkbox1, false);
        assertEquals(false, text1.getEnabled());
        assertEquals(true, text2.getEnabled());
        assertEquals(true, checkbox2.getSelection());
        setButtonSelectionWithEvents(checkbox2, false);
        assertEquals(true, text1.getEnabled());
        assertEquals(false, text2.getEnabled());
        assertEquals(true, checkbox1.getSelection());
    }

    public void testScenario13() {
        Text text = new Text(getComposite(), SWT.BORDER);
        
        getDbc().bindValue(SWTObservables.observeText(text, SWT.FocusOut), BeansObservables.observeValue(adventure, "name"));

        String adventureName = adventure.getName();
        assertEquals(adventureName, text.getText());
        enterText(text, "foobar");
        assertEquals("foobar", adventure.getName());
        adventure.setName("barfoo");
        assertEquals("barfoo", text.getText());
    }

    public void testScenario14() {
        Text t1 = new Text(getComposite(), SWT.BORDER);
        Text t2 = new Text(getComposite(), SWT.BORDER);
  
        getDbc().bindValue(SWTObservables.observeText(t1, SWT.Modify), BeansObservables.observeValue(adventure, "name"));
        getDbc().bindValue(SWTObservables.observeText(t2, SWT.Modify), BeansObservables.observeValue(adventure, "name"));
        
        final int[] counter = { 0 };
        
        IObservableValue uv = BeansObservables.observeValue(adventure, "name");
        
        uv.addChangeListener(new IChangeListener() {
            @Override
			public void handleChange(ChangeEvent event) {
                counter[0]++;
            }
        });

        String name = adventure.getName() + "Foo";
        enterText(t1, name);
        assertEquals(name, adventure.getName());
        assertEquals(name, t2.getText());
        assertTrue(counter[0] == 1);

        name = name + "Bar";
        uv.setValue(name);
        assertEquals(t1.getText(), adventure.getName());
        assertEquals(2, counter[0]);

    }

    public void testScenario15() {
        Text text = new Text(getComposite(), SWT.NONE);
        Account account = new Account();
        account.setExpiryDate(new Date());
        
        Binding b = getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), BeansObservables.observeValue(account, "expiryDate"));
        Text errorText = new Text(getComposite(), SWT.NONE);
        
        getDbc().bindValue(SWTObservables.observeText(errorText, SWT.Modify), b.getValidationStatus(), new UpdateValueStrategy(false, UpdateValueStrategy.POLICY_NEVER), null);
        assertTrue(((IStatus)b.getValidationStatus().getValue()).isOK());
        enterText(text, "foo");
        assertFalse(((IStatus)b.getValidationStatus().getValue()).isOK());
    }
}
