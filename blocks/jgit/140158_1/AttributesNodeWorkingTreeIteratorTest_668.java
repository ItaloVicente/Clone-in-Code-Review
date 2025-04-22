package org.eclipse.jgit.attributes;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.attributes.Attribute.State.SET;
import static org.eclipse.jgit.attributes.Attribute.State.UNSET;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.After;
import org.junit.Test;

public class AttributesNodeTest {
	private static final TreeWalk DUMMY_WALK = new TreeWalk(
			new InMemoryRepository(new DfsRepositoryDescription("FooBar")));

	private static final Attribute A_SET_ATTR = new Attribute("A"

	private static final Attribute A_UNSET_ATTR = new Attribute("A"

	private static final Attribute B_SET_ATTR = new Attribute("B"

	private static final Attribute B_UNSET_ATTR = new Attribute("B"

	private static final Attribute C_VALUE_ATTR = new Attribute("C"

	private static final Attribute C_VALUE2_ATTR = new Attribute("C"

	private InputStream is;

	@After
	public void after() throws IOException {
		if (is != null)
			is.close();
	}

	@Test
	public void testBasic() throws IOException {
		String attributeFileContent = "*.type1 A -B C=value\n"
				+ "*.type2 -A B C=value2";

		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
				asSet(A_SET_ATTR
		assertAttribute("file.type2"
				asSet(A_UNSET_ATTR
	}

	@Test
	public void testNegativePattern() throws IOException {
		String attributeFileContent = "!*.type1 A -B C=value\n"
				+ "!*.type2 -A B C=value2";

		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
		assertAttribute("file.type2"
	}

	@Test
	public void testEmptyNegativeAttributeKey() throws IOException {
				+ "*.type2 -   -A";
		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
		assertAttribute("file.type2"
	}

	@Test
	public void testEmptyValueKey() throws IOException {
				+ "*.type3 attr=\n";
		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
		assertAttribute("file.type2"
		assertAttribute("file.type3"
	}

	@Test
	public void testEmptyLine() throws IOException {
				+ "*.type2 -A B C=value2";

		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
				asSet(A_SET_ATTR
		assertAttribute("file.type2"
				asSet(A_UNSET_ATTR
	}

	@Test
	public void testTabSeparator() throws IOException {
		String attributeFileContent = "*.type1 \tA -B\tC=value\n"

		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
				asSet(A_SET_ATTR
		assertAttribute("file.type2"
				asSet(A_UNSET_ATTR
		assertAttribute("file.type3"
	}

	@Test
	public void testDoubleAsteriskAtEnd() throws IOException {

		is = new ByteArrayInputStream(attributeFileContent.getBytes(UTF_8));
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("dir"
				asSet(new Attribute[]{}));
		assertAttribute("dir/"
				asSet(new Attribute[]{}));
		assertAttribute("dir/file.type1"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/file.type1"
				asSet(A_SET_ATTR
	}

	private void assertAttribute(String path
			Attributes attrs) throws IOException {
		Attributes attributes = new Attributes();
		new AttributesHandler(DUMMY_WALK).mergeAttributes(node
				attributes);
		assertEquals(attrs
	}

	static Attributes asSet(Attribute... attrs) {
		return new Attributes(attrs);
	}

}
