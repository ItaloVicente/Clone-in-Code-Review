
package org.eclipse.core.tests.internal.databinding.validation;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.conversion.StringToCharacterConverter;
import org.eclipse.core.internal.databinding.validation.StringToCharacterValidator;

public class StringToCharacterValidatorTest extends TestCase {

	private StringToCharacterValidator validator;
	private StringToCharacterValidator primitiveValidator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		StringToCharacterConverter converter = StringToCharacterConverter
				.toCharacter(false);
		StringToCharacterConverter primitiveConverter = StringToCharacterConverter
				.toCharacter(true);
		validator = new StringToCharacterValidator(converter);
		primitiveValidator = new StringToCharacterValidator(primitiveConverter);
	}

	public void testValidatesCharacter() throws Exception {
		assertTrue(validator.validate("X").isOK());
	}

	public void testValidatesCharacterPrimitive() throws Exception {
		assertTrue(primitiveValidator.validate("X").isOK());
	}

	public void testNullCharacterIsValid() throws Exception {
		assertTrue(validator.validate(null).isOK());
	}

	public void testEmptyStringCharacterIsValid() throws Exception {
		assertTrue(validator.validate("").isOK());
	}

	public void testNullCharacterIsInvalidForPrimitive() throws Exception {
		assertFalse(primitiveValidator.validate(null).isOK());
	}

	public void testNonStringIsInvalid() throws Exception {
		assertFalse(primitiveValidator.validate(new Integer(4)).isOK());
	}

	public void testLongerThanOneCharacterIsInvalid() throws Exception {
		assertFalse(primitiveValidator.validate("XYZ").isOK());
	}

}
