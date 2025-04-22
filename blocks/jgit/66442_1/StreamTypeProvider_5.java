package org.eclipse.jgit.util.io;

import org.eclipse.jgit.attributes.AttributesProvider;
import org.eclipse.jgit.lib.CoreConfig.StreamType;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;

public class StreamTypeManager {
	private final WorkingTreeOptions options;

	private final OperationType op;

	private final AttributesProvider attributesProvider;

	public StreamTypeManager(WorkingTreeOptions options
			AttributesProvider attributesProvider) {
		this.options = options;
		this.op = op;
		this.attributesProvider = attributesProvider;
	}

	public StreamType getStreamType() {
		return StreamTypeUtil.detectStreamType(op
				attributesProvider.getAttributes());
	}
}
