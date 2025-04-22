package org.eclipse.e4.core.macros.tests;

import org.eclipse.e4.core.macros.internal.JSONHelper;
import org.junit.Assert;
import org.junit.Test;

public class JSONHelperTest {

	@Test
	public void testJSONHelper() {
		StringBuilder builder = new StringBuilder("chars\"\\/\b\f\n\r\t");
		builder.append(new Character((char) 5));
		builder.append(new Character((char) 6));
		String quoted = JSONHelper.quote(builder.toString());
		Assert.assertEquals("\"chars\\\"\\\\\\/\\b\\f\\n\\r\\t\\u0005\\u0006\"", quoted);
	}
}
