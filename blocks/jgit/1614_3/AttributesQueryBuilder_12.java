package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributesQueryBuilder {
	private final List<AttributesQuery> stack = new ArrayList<AttributesQuery>();

	public AttributesQuery addAttributes(String basePath
		if (basePath.startsWith("/")) {
			throw new RuntimeException();
		}

		while (stack.size() > 0 && !basePath.startsWith(stack.get(stack.size() - 1).getBasePath())) {
			stack.remove(stack.size() - 1);
		}

		final AttributesQuery parent = stack.size() > 0 ? stack.get(stack.size() - 1) : null;
		final AttributesQuery query = new AttributesQuery(attributes
		stack.add(query);
		return query;
	}
}
