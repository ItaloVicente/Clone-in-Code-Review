
package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.Test;

public class AttributesTest {

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

	@Test
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

	@Test
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

	@Test
	public void testLargeFile() throws IOException {
		final Attributes attributes = new Attributes();
		final StringBuilder builder = new StringBuilder();
		addLine(builder

		final Random random = new Random();
		final List<String> files = new ArrayList<String>();
		for (int index = 0; index < 10000; index++) {
			final StringBuilder pathName = new StringBuilder();
			pathName.append("/");

			final int segmentCount = 1 + Math.abs(random.nextInt()) % 5;
			for (int segmentIndex = 0; segmentIndex < segmentCount; segmentIndex++) {
				pathName.append((char)('a' + (Math.abs(random.nextInt()) % 26)));
				if (segmentIndex < segmentCount - 1) {
					pathName.append("/");
				}
			}
			final String file = pathName.toString() + ".txt";
			files.add(file);
			addLine(builder
		}

		final long startTime = System.currentTimeMillis();
		attributes.parse(new StringReader(builder.toString()));
		for (String file : files) {
			assertPathAttributes(attributes
		}
		final long endTime = System.currentTimeMillis();
		System.out.println("AttributesTest.testLargeFile took " + (endTime - startTime) + " ms.");
	}

	@Test
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
