package org.eclipse.jgit.attributes;

import java.util.Collection;

public interface MacroExpander {

	Collection<Attribute> expandMacro(Attribute attr);
}
