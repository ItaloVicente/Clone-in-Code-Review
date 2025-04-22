package org.eclipse.jgit.attributes;

import java.util.Set;

import org.eclipse.jgit.attributes.Attribute.State;

public class Attributes {


	private static Attribute IDENT_SET = new Attribute(IDENT_ATTR_NAME
			State.SET);

	private static Attribute IDENT_UNSET = new Attribute(IDENT_ATTR_NAME
			State.UNSET);

	Attributes() {
	}

	public static boolean hasIdentSet(Set<Attribute> attributes) {
		return attributes != null && attributes.contains(IDENT_SET);
	}

	public static boolean hasIdent(Set<Attribute> attributes) {
		if (attributes == null)
			return false;
		return attributes.contains(IDENT_UNSET)
				|| attributes.contains(IDENT_SET);
	}

}
