package org.eclipse.jgit.patch;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

public class ApplyError {

	private FileHeader fileHeader;

	private ChangeType changeType;

	private HunkHeader hunkHeader;

	public ApplyError(FileHeader fh
		this.fileHeader = fh;
		this.changeType = t;
	}

	public ApplyError(HunkHeader hh
		this(hh.getFileHeader()
		this.hunkHeader = hh;

	}

	public FileHeader getFileHeader() {
		return fileHeader;
	}

	public HunkHeader getHunkHeader() {
		return hunkHeader;
	}

	public ChangeType getChangeType() {
		return changeType;
	}
}
