package org.eclipse.jgit.util.io;

import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.lib.CoreConfig.StreamType;

public interface StreamTypeProvider {

	public StreamType getStreamType();

}
