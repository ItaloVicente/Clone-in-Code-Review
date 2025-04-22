
package org.eclipse.core.tests.internal.databinding.conversion;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.eclipse.core.internal.databinding.BindingMessages;
import org.eclipse.core.internal.databinding.conversion.StringToBooleanConverter;

public class StringToBooleanConverterTest extends TestCase {
	private StringToBooleanConverter converter;

	private List trueValues;

	private List falseValues;

	@Override
	public void run(TestResult result) {
		trueValues = Collections.unmodifiableList(toValues(BindingMessages
				.getString("ValueDelimiter"), BindingMessages
				.getString("TrueStringValues")));
		falseValues = Collections.unmodifiableList(toValues(BindingMessages
				.getString("ValueDelimiter"), BindingMessages
				.getString("FalseStringValues")));

		super.run(result);
	}

	private List toValues(String delimiter, String values) {
		StringTokenizer tokenizer = new StringTokenizer(values, delimiter);
		List result = new LinkedList();

		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}

		return result;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		converter = new StringToBooleanConverter();
		assertTrue(trueValues.size() > 0);
		assertTrue(falseValues.size() > 0);
	}

	public void testConvertsToTrue() throws Exception {
		Boolean result = (Boolean) converter.convert(trueValues.get(0));
		assertTrue(result.booleanValue());
	}

	public void testConvertsToFalse() throws Exception {
		Boolean result = (Boolean) converter.convert(falseValues.get(0));
		assertFalse(result.booleanValue());
	}

	public void testUpperCaseStringConvertsToTrue() throws Exception {
		Boolean result = (Boolean) converter.convert(((String) trueValues.get(0))
				.toUpperCase());
		assertTrue(result.booleanValue());
	}

	public void testUpperCaseStringConvertsToFalse() throws Exception {
		Boolean result = (Boolean) converter.convert(((String) falseValues.get(0))
				.toUpperCase());
		assertFalse(result.booleanValue());
	}
}
