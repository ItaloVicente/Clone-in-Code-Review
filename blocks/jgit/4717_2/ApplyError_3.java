package org.eclipse.jgit.patch;

import java.io.IOException;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

public class ApplyError {

	private FileHeader fileHeader;

	private ChangeType changeType;

	private char hunkContorlChar;

	private HunkHeader hunkHeader;

	private IOException ioException;

	public ApplyError(FileHeader fh
		this.fileHeader = fh;
		this.changeType = t;
		this.hunkContorlChar = c;
	}

	public ApplyError(FileHeader fh
		this(fh

	}

	public ApplyError(HunkHeader hh
		this(hh.getFileHeader()
		this.hunkHeader = hh;
	}

	public ApplyError(IOException e) {
		this.ioException = e;
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

	public char getHunkControlChar() {
		return hunkContorlChar;
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		if (ioException != null) {
			r.append(ioException.getMessage());
		} else {
			r.append(getChangeType().name());
			if (getHunkControlChar() != 0) {
				r.append("
			r.append(getHunkHeader());
			r.append(getFileHeader());
		}
		return r.toString();
	}
}
