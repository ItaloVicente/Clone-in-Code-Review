package org.eclipse.egit.core;

import org.eclipse.jgit.merge.MergeStrategy;

public class MergeStrategyDescriptor {
	private final String name;

	private final String label;

	private final Class<?> implementedBy;

	public MergeStrategyDescriptor(String name, String label,
			Class<?> implementedBy) {
		this.name = name;
		this.label = label;
		this.implementedBy = implementedBy;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public Class<?> getImplementedBy() {
		return implementedBy;
	}
}
