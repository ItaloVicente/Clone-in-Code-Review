
package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.Test;

public class AttributesQueryTest {

	private static final Attribute DELTA_SET = new Attribute("delta"
			AttributeValue.SET);

	private static final Attribute DELTA_UNSET = new Attribute("delta"
			AttributeValue.UNSET);

	private static final Attribute EOL_CRLF = new Attribute("eol"
			AttributeValue.createValue("crlf"));

	private static final Attribute EOL_LF = new Attribute("eol"
			AttributeValue.createValue("lf"));

	private static final Attribute EOL_SET = new Attribute("eol"
			AttributeValue.SET);

	private static final Attribute TEXT_AUTO = new Attribute("text"
			AttributeValue.createValue("auto"));

	private static final Attribute TEXT_UNSET = new Attribute("text"
			AttributeValue.UNSET);

	@Test
	public void test() throws IOException {
		final StringBuilder gitInfoAttributesBuilder = new StringBuilder();
		addLine(gitInfoAttributesBuilder
		addLine(gitInfoAttributesBuilder
		final Attributes gitInfoAttributes = new Attributes();
		gitInfoAttributes.parse(new StringReader(gitInfoAttributesBuilder
				.toString()));

		final StringBuilder rootBuilder = new StringBuilder();
		addLine(rootBuilder
		addLine(rootBuilder
		final Attributes rootAttributes = new Attributes();
		rootAttributes.parse(new StringReader(rootBuilder.toString()));
		final AttributesQuery rootQuery = new AttributesQuery(rootAttributes
				""

		final StringBuilder dirBuilder = new StringBuilder();
		addLine(dirBuilder
		addLine(dirBuilder
		final Attributes dirAttributes = new Attributes();
		dirAttributes.parse(new StringReader(dirBuilder.toString()));
		final AttributesQuery dirQuery = new AttributesQuery(dirAttributes
				"dir"

		final StringBuilder dirSubdirBuilder = new StringBuilder();
		addLine(dirSubdirBuilder
		final Attributes dirSubdirAttributes = new Attributes();
		dirSubdirAttributes
				.parse(new StringReader(dirSubdirBuilder.toString()));
		final AttributesQuery dirSubdirQuery = new AttributesQuery(
				dirSubdirAttributes

		final StringBuilder dirSubdir2Builder = new StringBuilder();
		addLine(dirSubdir2Builder
		final Attributes dirSubdir2Attributes = new Attributes();
		dirSubdir2Attributes.parse(new StringReader(dirSubdir2Builder
				.toString()));
		final AttributesQuery dirSubdir2Query = new AttributesQuery(
				dirSubdir2Attributes

		AttributesQuery query = new AttributesQuery(gitInfoAttributes
				rootQuery);
		assertPathAttributes(query
		assertPathAttributes(query
		assertPathAttributes(query
		assertPathAttributes(query

		query = new AttributesQuery(gitInfoAttributes
		assertPathAttributes(query
		assertPathAttributes(query
		assertPathAttributes(query
				DELTA_UNSET

		query = new AttributesQuery(gitInfoAttributes
		assertPathAttributes(query
		assertPathAttributes(query
		assertPathAttributes(query
				DELTA_UNSET);
		assertPathAttributes(query
				TEXT_AUTO
		assertPathAttributes(query
				TEXT_AUTO

		query = new AttributesQuery(gitInfoAttributes
		assertPathAttributes(query
				DELTA_UNSET);
		assertPathAttributes(query
				DELTA_UNSET);
		assertPathAttributes(query
				DELTA_UNSET);
	}

	public void testManPageExample() throws IOException {
		final StringBuilder gitInfoAttributesBuilder = new StringBuilder();
		addLine(gitInfoAttributesBuilder
		final Attributes gitInfoAttributes = new Attributes();
		gitInfoAttributes.parse(new StringReader(gitInfoAttributesBuilder
				.toString()));

		final StringBuilder rootBuilder = new StringBuilder();
		addLine(rootBuilder
		final Attributes rootAttributes = new Attributes();
		rootAttributes.parse(new StringReader(rootBuilder.toString()));
		final AttributesQuery rootQuery = new AttributesQuery(rootAttributes
				""

		final StringBuilder tBuilder = new StringBuilder();
		addLine(tBuilder
		addLine(tBuilder
		addLine(tBuilder

		final Attributes tAttributes = new Attributes();
		tAttributes.parse(new StringReader(tBuilder.toString()));
		final AttributesQuery tQuery = new AttributesQuery(tAttributes
				rootQuery);

		final AttributesQuery query = new AttributesQuery(gitInfoAttributes
				""
		assertPathAttributes(query
				AttributeValue.SET)
				new Attribute("baz"
						"merge"
	}

	private void addLine(StringBuilder builder
		builder.append(str);
		builder.append("\n");
	}

	private static void assertPathAttributes(AttributesQuery query
			String path
		final AttributesAllCollector allCollector = new AttributesAllCollector();
		query.collect(path
		assertEquals(Arrays.asList(expected)

		for (Attribute attribute : expected) {
			final AttributeValueCollector valueCollector = new AttributeValueCollector(
					attribute.getKey());
			query.collect(path
			assertEquals(attribute.getValue()
		}
	}
}
