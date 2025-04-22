package org.eclipse.jgit.attributes;

import static org.eclipse.jgit.attributes.Attribute.State.SET;
import static org.eclipse.jgit.attributes.Attribute.State.UNSET;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

public class AtributeNodeTest {

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

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
				asSet(A_SET_ATTR
		assertAttribute("file.type2"
				asSet(A_UNSET_ATTR
	}

	@Test
	public void testEmptyLine() throws IOException {
				+ "*.type2 -A B C=value2";

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
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

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("file.type1"
				asSet(A_SET_ATTR
		assertAttribute("file.type2"
				asSet(A_UNSET_ATTR
		assertAttribute("file.type3"
	}

	private void assertAttribute(String path
			Set<Attribute> attrs) {
		HashMap<String
		node.getAttributes(path
		assertEquals(attrs
	}

	static Set<Attribute> asSet(Attribute... attrs) {
		Set<Attribute> result = new HashSet<Attribute>();
		for (Attribute attr : attrs)
			result.add(attr);
		return result;
	}

}
