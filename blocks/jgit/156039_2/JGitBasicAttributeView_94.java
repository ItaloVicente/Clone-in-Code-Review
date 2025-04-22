package org.eclipse.jgit.niofs.internal;

import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;

public class HiddenAttributesImpl extends AbstractJGitBasicAttributesImpl implements HiddenAttributes {

	private final boolean hidden;

	public HiddenAttributesImpl(final BasicFileAttributes attributes
		super(attributes);
		this.hidden = isHidden;
	}

	@Override
	public boolean isHidden() {
		return this.hidden;
	}
}
