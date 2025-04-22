package org.eclipse.jgit.attributes;

import java.util.Collection;
import java.util.Collections;

public class NullMacroExpander implements MacroExpander {
	@Override
	public Collection<Attribute> expandMacro(Attribute attr) {
		return Collections.singleton(attr);
	}
}
