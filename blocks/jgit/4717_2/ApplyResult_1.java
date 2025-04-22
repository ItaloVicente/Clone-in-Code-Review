package org.eclipse.jgit.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.ApplyError;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.patch.Patch;
import org.eclipse.jgit.util.IO;

public class ApplyCommand extends GitCommand<ApplyResult> {

	private InputStream in;

	ApplyCommand(Repository repo) {
		super(repo);
	}

	public ApplyCommand setPatch(InputStream in) {
		this.in = in;
		return this;
	}

	public ApplyResult call() {
		ApplyResult r = new ApplyResult();
		try {
			final Patch p = new Patch();
			try {
				p.parse(in);
			} finally {
				in.close();
			}
			if (!p.getErrors().isEmpty())
				return r.setFormatErrors(p.getErrors());
			for (FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				File f = null;
				switch (type) {
				case ADD:
					f = getFile(fh.getNewPath()
					apply(f
					break;
				case MODIFY:
					f = getFile(fh.getOldPath()
					apply(f
					break;
				case DELETE:
					f = getFile(fh.getOldPath()
					if (!f.delete())
						r.addApplyError(new ApplyError(fh
					break;
				case RENAME:
					f = getFile(fh.getOldPath()
					File dest = getFile(fh.getNewPath()
					if (!f.renameTo(dest))
						r.addApplyError(new ApplyError(fh
					break;
				case COPY:
					f = getFile(fh.getOldPath()
					byte[] bs = IO.readFully(f);
					FileWriter fw = new FileWriter(getFile(fh.getNewPath()
							true));
					fw.write(new String(bs));
					fw.close();
				}
			}
		} catch (IOException e) {
			r.addApplyError(new ApplyError(e));
		}
		return r;
	}

	private File getFile(String path
		File f = new File(getRepository().getWorkTree()
		if (create)
			f.createNewFile();
		return f;
	}

	private void apply(File f
		RawText rt = new RawText(f);
		List<String> oldLines = new ArrayList<String>(rt.size());
		for (int i = 0; i < rt.size(); i++) {
			oldLines.add(rt.getString(i));
		}
		List<String> newLines = new ArrayList<String>(oldLines);
		HUNKS: for (HunkHeader hh : fh.getHunks()) {
			StringBuilder hunk = new StringBuilder();
			for (int j = hh.getStartOffset(); j < hh.getEndOffset(); j++) {
				hunk.append((char) hh.getBuffer()[j]);
			}
			RawText hrt = new RawText(hunk.toString().getBytes());
			List<String> hunkLines = new ArrayList<String>(hrt.size());
			for (int i = 0; i < hrt.size(); i++) {
				hunkLines.add(hrt.getString(i));
			}
			int pos = 0;
			for (int j = 1; j < hunkLines.size(); j++) {
				String hunkLine = hunkLines.get(j);
				switch (hunkLine.charAt(0)) {
				case ' ':
					if (!newLines.get(hh.getNewStartLine() - 1 + pos).equals(
							hunkLine.substring(1))) {
						r.addApplyError(new ApplyError(hh
						continue HUNKS;
					}
					pos++;
					break;
				case '-':
					if (!newLines.get(hh.getNewStartLine() - 1 + pos).equals(
							hunkLine.substring(1))) {
						r.addApplyError(new ApplyError(hh
						continue HUNKS;
					}
					newLines.remove(hh.getNewStartLine() - 1 + pos);
					break;
				case '+':
					newLines.add(hh.getNewStartLine() - 1 + pos
							hunkLine.substring(1));
					pos++;
					break;
				}
			}
		}
		if (!isNoNewlineAtEndOfFile(fh))
			newLines.add("");
		if (!rt.isMissingNewlineAtEnd())
			oldLines.add("");
		if (!isChanged(oldLines
		StringBuilder sb = new StringBuilder();
		for (String l : newLines) {
		}
		sb.deleteCharAt(sb.length() - 1);
		FileWriter fw = new FileWriter(f);
		fw.write(sb.toString());
		fw.close();
	}

	private boolean isChanged(List<String> ol
		if (ol.size() != nl.size())
			return true;
		for (int i = 0; i < ol.size(); i++) {
			if (!ol.get(i).equals(nl.get(i)))
				return true;
		}
		return false;
	}

	private boolean isNoNewlineAtEndOfFile(FileHeader fh) {
		HunkHeader lastHunk = fh.getHunks().get(fh.getHunks().size() - 1);
		RawText lhrt = new RawText(lastHunk.getBuffer());
		return lhrt.getString(lhrt.size() - 1).equals(
	}
}
