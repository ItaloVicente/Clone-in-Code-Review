package org.eclipse.e4.ui.css.core.dom;

import java.util.stream.Stream;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface IStreamingNodeList {
	public Stream<Node> stream();
}
