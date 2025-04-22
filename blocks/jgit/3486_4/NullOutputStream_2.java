package org.eclipse.jgit.diff;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.io.NullOutputStream;

public class PatchIdDiffFormatter extends DiffFormatter {

	private final MessageDigest digest;

	public PatchIdDiffFormatter() {
		super(new DigestOutputStream(NullOutputStream.INSTANCE
				Constants.newMessageDigest()));
		digest = ((DigestOutputStream) getOutputStream()).getMessageDigest();
	}

	public ObjectId getCalulatedPatchId() {
		return ObjectId.fromRaw(digest.digest());
	}

	@Override
	protected void writeHunkHeader(int aStartLine
			int bStartLine
	}

	@Override
	protected void formatIndexLine(OutputStream o
			throws IOException {
	}
}
