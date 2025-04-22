package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
import org.eclipse.jgit.niofs.internal.FileDiffImpl;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.patch.FileHeader;

public class DiffBranches {

	private final Git git;
	private final String branchA;
	private final String branchB;

	public DiffBranches(Git git
		this.git = checkNotNull("git"
		this.branchA = checkNotEmpty("branchA"
		this.branchB = checkNotEmpty("branchB"
	}

	public List<FileDiff> execute() {
		final List<FileDiff> diffs = new ArrayList<>();

		final List<DiffEntry> result = git.listDiffs(git.getTreeFromRef(this.branchA)
				git.getTreeFromRef(this.branchB));

		final DiffFormatter formatter = createFormatter();

		result.forEach(elem -> {
			final FileHeader header = getFileHeader(formatter
			header.toEditList().forEach(edit -> diffs.add(createFileDiff(elem
		});

		return diffs;
	}

	private FileHeader getFileHeader(final DiffFormatter formatter
		try {
			return formatter.toFileHeader(elem);
		} catch (IOException e) {
			throw new GitException("A problem occurred when trying to obtain diffs between files"
		}
	}

	private DiffFormatter createFormatter() {

		OutputStream outputStream = new ByteArrayOutputStream();
		DiffFormatter formatter = new DiffFormatter(outputStream);
		formatter.setRepository(git.getRepository());
		return formatter;
	}

	private FileDiff createFileDiff(final DiffEntry elem
		try {
			final String changeType = header.getChangeType().toString();
			final int startA = edit.getBeginA();
			final int endA = edit.getEndA();
			final int startB = edit.getBeginB();
			final int endB = edit.getEndB();

			String pathA = header.getOldPath();
			String pathB = header.getNewPath();

			final List<String> linesA = getLines(elem.getOldId().toObjectId()
			final List<String> linesB = getLines(elem.getNewId().toObjectId()

			return new FileDiffImpl(pathA
		} catch (IOException e) {
			throw new GitException("A problem occurred when trying to obtain diffs between files"
		}
	}

	private List<String> getLines(final ObjectId id
		List<String> lines = new ArrayList<>();
		if (!id.equals(ObjectId.zeroId())) {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			final ObjectLoader loader = git.getRepository().open(id);
			loader.copyTo(stream);
			final String content = stream.toString();
			final List<String> filteredLines = Arrays.asList(content.split("\n"));
			lines = filteredLines.subList(fromStart
		}
		return lines;
	}
}
