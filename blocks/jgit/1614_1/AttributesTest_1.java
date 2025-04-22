
package org.eclipse.jgit.attributes;

import junit.framework.*;

import java.io.*;
import java.util.*;

public class AttributesTest extends TestCase {

	private static final Attribute DELTA_SET = new Attribute("delta"
			AttributeValue.SET);

	private static final Attribute EOL_CRLF = new Attribute("eol"
			AttributeValue.createValue("crlf"));

	private static final Attribute EOL_LF = new Attribute("eol"
			AttributeValue.createValue("lf"));

	private static final Attribute EOL_SET = new Attribute("eol"
			AttributeValue.SET);

	private static final Attribute TEXT_AUTO = new Attribute("text"
			AttributeValue.createValue("auto"));

	private static final Attribute TEXT_SET = new Attribute("text"
			AttributeValue.SET);

	private static final Attribute TEXT_UNSET = new Attribute("text"
			AttributeValue.UNSET);

	public void testParsing() throws IOException {
		final Attributes attributes = new Attributes();
		attributes.parse(new StringReader(createSampleAttributeContent()));

		final List<String> patterns = attributes.getPatterns();
		assertEquals(Arrays.asList("*.x"
				"file3.txt"
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
				AttributeValue.createValue("oof"))
				AttributeValue.SET)
	}

	public void testDefinitions() throws IOException {
		final Attributes attributes = new Attributes();
		final StringBuilder builder = new StringBuilder();
		final String str = "*.txt text=auto";
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder

		attributes.parse(new StringReader(builder.toString()));
		assertPathAttributes(attributes
		assertPathAttributes(attributes
				TEXT_AUTO);
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
		assertPathAttributes(attributes
	}

	public void testReadWrite() throws IOException {
		final Attributes attributes = new Attributes();
		final String content = createSampleAttributeContent();
		attributes.parse(new StringReader(content));
		assertEquals(content
	}

	private String createSampleAttributeContent() {
		final StringBuilder builder = new StringBuilder();
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		addLine(builder
		return builder.toString();
	}

	private void addLine(StringBuilder builder
		builder.append(str);
		builder.append("\n");
	}

	private static void assertPathAttributes(Attributes attributes
			String path
		final AttributesAllCollector allCollector = new AttributesAllCollector();
		attributes.collect(path
		assertEquals(Arrays.asList(expected)

		for (Attribute attribute : expected) {
			final AttributeValueCollector valueCollector = new AttributeValueCollector(
					attribute.getKey());
			attributes.collect(path
			assertEquals(attribute.getValue()
		}
	}
}
