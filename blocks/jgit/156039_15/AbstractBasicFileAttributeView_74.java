package org.eclipse.jgit.niofs.fs.options;

import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;

public class SquashOption extends CommentedOption {

	public static final String SQUASH_ATTR = "SQUASH_ATTR";
	public VersionRecord versionRecord;

	public SquashOption(VersionRecord record) {
		super(null
		this.setRecord(record);
	}

	public VersionRecord getRecord() {
		return versionRecord;
	}

	public void setRecord(final VersionRecord versionRecord) {
		this.versionRecord = versionRecord;
	}
}
