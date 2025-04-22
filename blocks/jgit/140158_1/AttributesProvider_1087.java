package org.eclipse.jgit.attributes;

import java.io.IOException;

import org.eclipse.jgit.lib.CoreConfig;

public interface AttributesNodeProvider {

	AttributesNode getInfoAttributesNode() throws IOException;

	AttributesNode getGlobalAttributesNode() throws IOException;

}
