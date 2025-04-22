
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.lib.RebaseTodoLine.Action;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class RebaseTodoFile {
	private Repository repo;

	public RebaseTodoFile(Repository repo) {
		this.repo = repo;
	}

	public List<RebaseTodoLine> readRebaseTodo(String path
			boolean includeComments) throws IOException {
		byte[] buf = IO.readFully(new File(repo.getDirectory()
		int ptr = 0;
		int tokenBegin = 0;
		List<RebaseTodoLine> r = new LinkedList<>();
		while (ptr < buf.length) {
			tokenBegin = ptr;
			ptr = RawParseUtils.nextLF(buf
			int lineStart = tokenBegin;
			int lineEnd = ptr - 2;
			if (lineEnd >= 0 && buf[lineEnd] == '\r')
				lineEnd--;
			if (buf[tokenBegin] == '#') {
				if (includeComments)
					parseComments(buf
			} else {
				tokenBegin = nextParsableToken(buf
				if (tokenBegin == -1) {
					if (includeComments)
						r.add(new RebaseTodoLine(RawParseUtils.decode(buf
								lineStart
					continue;
				}
				RebaseTodoLine line = parseLine(buf
				if (line == null)
					continue;
				r.add(line);
			}
		}
		return r;
	}

	private static void parseComments(byte[] buf
			List<RebaseTodoLine> r
		RebaseTodoLine line = null;
		String commentString = RawParseUtils.decode(buf
				tokenBegin
		try {
			skip = nextParsableToken(buf
			if (skip != -1) {
				line = parseLine(buf
				line.setAction(Action.COMMENT);
				line.setComment(commentString);
			}
		} catch (Exception e) {
			line = null;
		} finally {
			if (line == null)
				line = new RebaseTodoLine(commentString);
			r.add(line);
		}
	}

	private static int nextParsableToken(byte[] buf
		while (tokenBegin <= lineEnd
				&& (buf[tokenBegin] == ' ' || buf[tokenBegin] == '\t' || buf[tokenBegin] == '\r'))
			tokenBegin++;
		if (tokenBegin > lineEnd)
			return -1;
		return tokenBegin;
	}

	private static RebaseTodoLine parseLine(byte[] buf
			int lineEnd) {
		RebaseTodoLine.Action action = null;
		AbbreviatedObjectId commit = null;

		int nextSpace = RawParseUtils.next(buf
		int tokenCount = 0;
		while (tokenCount < 3 && nextSpace < lineEnd) {
			switch (tokenCount) {
			case 0:
				String actionToken = new String(buf
						nextSpace - tokenBegin - 1
				tokenBegin = nextSpace;
				action = RebaseTodoLine.Action.parse(actionToken);
				if (action == null)
				break;
			case 1:
				nextSpace = RawParseUtils.next(buf
				String commitToken = new String(buf
						nextSpace - tokenBegin - 1
				tokenBegin = nextSpace;
				commit = AbbreviatedObjectId.fromString(commitToken);
				break;
			case 2:
				return new RebaseTodoLine(action
						RawParseUtils.decode(buf
			}
			tokenCount++;
		}
		if (tokenCount == 2)
			return new RebaseTodoLine(action
		return null;
	}

	public void writeRebaseTodoFile(String path
			boolean append) throws IOException {
		try (OutputStream fw = new BufferedOutputStream(new FileOutputStream(
				new File(repo.getDirectory()
			StringBuilder sb = new StringBuilder();
			for (RebaseTodoLine step : steps) {
				sb.setLength(0);
				if (RebaseTodoLine.Action.COMMENT.equals(step.action))
					sb.append(step.getComment());
				else {
					sb.append(step.getAction().toToken());
					sb.append(step.getCommit().name());
					sb.append(step.getShortMessage().trim());
				}
				sb.append('\n');
				fw.write(Constants.encode(sb.toString()));
			}
		}
	}
}
