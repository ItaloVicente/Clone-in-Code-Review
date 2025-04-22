package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.patch.Patch;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;

public class ApplyCommand extends GitCommand<ApplyResult> {

	private InputStream in;

	ApplyCommand(Repository repo) {
		super(repo);
	}

	public ApplyCommand setPatch(InputStream in) {
		checkCallable();
		this.in = in;
		return this;
	}

	@Override
	public ApplyResult call() throws GitAPIException
			PatchApplyException {
		checkCallable();
		ApplyResult r = new ApplyResult();
		try {
			final Patch p = new Patch();
			try {
				p.parse(in);
			} finally {
				in.close();
			}
			if (!p.getErrors().isEmpty())
				throw new PatchFormatException(p.getErrors());
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
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().cannotDeleteFile
					break;
				case RENAME:
					f = getFile(fh.getOldPath()
					File dest = getFile(fh.getNewPath()
					try {
						FileUtils.rename(f
								StandardCopyOption.ATOMIC_MOVE);
					} catch (IOException e) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().renameFileFailed
					}
					break;
				case COPY:
					f = getFile(fh.getOldPath()
					byte[] bs = IO.readFully(f);
					FileOutputStream fos = new FileOutputStream(getFile(
							fh.getNewPath()
							true));
					try {
						fos.write(bs);
					} finally {
						fos.close();
					}
				}
				r.addUpdatedFile(f);
			}
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException
		}
		setCallable(false);
		return r;
	}

	private File getFile(String path
			throws PatchApplyException {
		File f = new File(getRepository().getWorkTree()
		if (create)
			try {
				File parent = f.getParentFile();
				FileUtils.mkdirs(parent
				FileUtils.createNewFile(f);
			} catch (IOException e) {
				throw new PatchApplyException(MessageFormat.format(
						JGitText.get().createNewFileFailed
			}
		return f;
	}

	private void apply(File f
			throws IOException
		RawText rt = new RawText(f);
		List<String> oldLines = new ArrayList<>(rt.size());
		for (int i = 0; i < rt.size(); i++)
			oldLines.add(rt.getString(i));
		List<String> newLines = new ArrayList<>(oldLines);
		for (HunkHeader hh : fh.getHunks()) {

			byte[] b = new byte[hh.getEndOffset() - hh.getStartOffset()];
			System.arraycopy(hh.getBuffer()
					b.length);
			RawText hrt = new RawText(b);

			List<String> hunkLines = new ArrayList<>(hrt.size());
			for (int i = 0; i < hrt.size(); i++)
				hunkLines.add(hrt.getString(i));
			int pos = 0;
			for (int j = 1; j < hunkLines.size(); j++) {
				String hunkLine = hunkLines.get(j);
				switch (hunkLine.charAt(0)) {
				case ' ':
					if (!newLines.get(hh.getNewStartLine() - 1 + pos).equals(
							hunkLine.substring(1))) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().patchApplyException
					}
					pos++;
					break;
				case '-':
					if (hh.getNewStartLine() == 0) {
						newLines.clear();
					} else {
						if (!newLines.get(hh.getNewStartLine() - 1 + pos)
								.equals(hunkLine.substring(1))) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().patchApplyException
						}
						newLines.remove(hh.getNewStartLine() - 1 + pos);
					}
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
		if (!rt.isMissingNewlineAtEnd())
		if (!isChanged(oldLines
		StringBuilder sb = new StringBuilder();
		for (String l : newLines) {
			sb.append(l).append('\n');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		try (Writer fw = new OutputStreamWriter(new FileOutputStream(f)
				UTF_8)) {
			fw.write(sb.toString());
		}

		getRepository().getFS().setExecute(f
	}

	private static boolean isChanged(List<String> ol
		if (ol.size() != nl.size())
			return true;
		for (int i = 0; i < ol.size(); i++)
			if (!ol.get(i).equals(nl.get(i)))
				return true;
		return false;
	}

	private boolean isNoNewlineAtEndOfFile(FileHeader fh) {
		HunkHeader lastHunk = fh.getHunks().get(fh.getHunks().size() - 1);
		RawText lhrt = new RawText(lastHunk.getBuffer());
		return lhrt.getString(lhrt.size() - 1).equals(
	}
}
