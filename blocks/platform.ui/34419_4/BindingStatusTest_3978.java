package org.eclipse.core.tests.internal.databinding;
import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.BindingMessages;


public class BindingMessagesTest extends TestCase {
	public void testFormatString() throws Exception {
		String key = "Validate_NumberOutOfRangeError";
		String result = BindingMessages.formatString(key, new Object[] {"1", "2"});
		assertFalse("key should not be returned", key.equals(result));
	}

	public void testFormatStringForKeyNotFound() throws Exception {
		String key = "key_that_does_not_exist";
		String result = BindingMessages.formatString(key, null);
		assertTrue(key.equals(result));
	}
}
