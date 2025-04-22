package org.eclipse.jgit.lib;

import org.eclipse.jgit.attributes.Attributes;

public class GitAttributes {



	public static boolean isBinary(Attributes attributes) {
		if (attributes != null) {
			if (attributes.isUnset(GitAttributes.MERGE)) {
				return true;
			} else if (attributes.isCustom(GitAttributes.MERGE)
					&& attributes.getValue(GitAttributes.MERGE).equals(
							GitAttributes.BUILTIN_BINARY_MERGER)) {
				return true;
			}
		}
		return false;
	}

}
