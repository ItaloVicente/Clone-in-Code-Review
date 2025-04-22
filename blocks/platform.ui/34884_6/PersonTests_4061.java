
package org.eclipse.jface.tests.examples.databinding.mask.internal;

import junit.framework.TestCase;

import org.eclipse.jface.examples.databinding.mask.EditMaskParseException;
import org.eclipse.jface.examples.databinding.mask.internal.EditMaskParser;

public class EditMaskParserTest extends TestCase {

	private EditMaskParser parser;

	@Override
	protected void setUp() throws Exception {
		parser = new EditMaskParser("(###) ###-####");
	}

	public void testEditMaskParser_validMask() {
		new EditMaskParser("(###) ###-####");
	}

	public void testEditMaskParser_invalidMask() {
		try {
			new EditMaskParser("(###) ###-####\\");
			fail("Should have thrown exception");
		} catch (EditMaskParseException e) {
		}
	}

	public void testSetInput() {
		parser.setInput("63a0) 5*55-1\\212abc9");
		assertEquals("Unformatted input", "6305551212", parser.getRawResult());
		assertEquals("Formatted input", "(630) 555-1212", parser.getFormattedResult());
	}

	public void testSetInput_incomplete() {
		parser.setInput("6a0) 5*5-1\\12");
		assertEquals("Unformatted input", "6055112", parser.getRawResult());
		assertEquals("Formatted input", "(605) 511-2   ", parser.getFormattedResult());
	}

	public void testIsComplete() {
		parser.setInput("63a0) 5*55-1\\212");
		assertTrue("complete", parser.isComplete());
		parser.setInput("6a0) 5*5-1\\12");
		assertFalse("incomplete", parser.isComplete());
	}
	
	public void testSetPlaceholder() throws Exception {
		parser.setInput("6a0) 5*5-1\\12");
		assertEquals("Formatted input", "(605) 511-2   ", parser.getFormattedResult());
		parser.setPlaceholder('_');
		assertEquals("Formatted input", "(605) 511-2___", parser.getFormattedResult());
	}

	public void testGetNextInputPosition() {
		assertEquals("Skip leading (", 1, parser.getNextInputPosition(0));
		assertEquals("Position 1 is good", 1, parser.getNextInputPosition(1));
		assertEquals("Skip )<space>", 6, parser.getNextInputPosition(4));
	}
	
	public void testGetFirstIncompleteInputPosition() throws Exception {
		assertEquals("1st position incomplete", 1, parser.getFirstIncompleteInputPosition());
		parser.setInput("6a0) 5*5-1\\12");
		assertEquals("11th position incomplete", 11, parser.getFirstIncompleteInputPosition());
		parser.setInput("63a0) 5*55-1\\212");
		assertEquals("all complete", -1, parser.getFirstIncompleteInputPosition());
	}
}

