package org.eclipse.jgit.util.io;

import org.eclipse.jgit.attributes.AttributesProvider;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;

public class EolStreamTypeDetector {
	private final WorkingTreeOptions options;

	private final OperationType op;

	private final AttributesProvider attributesProvider;

	public EolStreamTypeDetector(WorkingTreeOptions options
			AttributesProvider attributesProvider) {
		this.options = options;
		this.op = op;
		this.attributesProvider = attributesProvider;
	}

	public EolStreamType getStreamType() {
		return EolStreamTypeUtil.detectStreamType(op
				attributesProvider.getAttributes());
	}
}
