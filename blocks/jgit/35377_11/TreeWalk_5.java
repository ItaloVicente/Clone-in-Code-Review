package org.eclipse.jgit.treewalk;

import java.io.IOException;

import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.lib.CoreConfig;

public interface AttributesNodeProvider {

	public AttributesNode getInfoAttributesNode() throws IOException;

	public AttributesNode getGlobalAttributesNode() throws IOException;

}
