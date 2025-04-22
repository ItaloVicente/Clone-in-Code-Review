package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Test;

public class CrLfNativeTest extends RepositoryTestCase {

	@Test
	public void checkoutWithCrLfNativeUnix() throws Exception {
		verifyNativeCheckout(new MockSystemReader() {
			{
				setUnix();
			}
		});
	}

	@Test
	public void checkoutWithCrLfNativeWindows() throws Exception {
		verifyNativeCheckout(new MockSystemReader() {
			{
				setWindows();
			}
		});
	}

	public void verifyNativeCheckout(SystemReader systemReader)
			throws Exception {
		SystemReader.setInstance(systemReader);
		Git git = Git.wrap(db);
		FileBasedConfig config = db.getConfig();
		config.setString("core"
		config.setString("core"
		config.save();
		writeTrashFile(".gitattributes"
		File file = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").addFilepattern(".gitattributes")
				.call();
		git.commit().setMessage("Initial").call();
		assertEquals(
				"[.gitattributes
						+ "[file.txt
				indexState(CONTENT));
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("New commit").call();
		git.reset().setMode(ResetType.HARD).setRef("HEAD~").call();
		checkFile(file
				: "line 1\nline 2\n");
		Status status = git.status().call();
		assertTrue("git status should be clean"
	}

	@Test
	public void testCrLfAttribute() throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setString("core"
		config.setString("core"
		config.save();
		writeTrashFile(".gitattributes"
				"*.txt text\n*.crlf crlf\n*.bin -text\n*.nocrlf -crlf\n*.input crlf=input\n*.eol eol=lf");
		writeTrashFile("foo.txt"
		writeTrashFile("foo.crlf"
		writeTrashFile("foo.bin"
		writeTrashFile("foo.nocrlf"
		writeTrashFile("foo.input"
		writeTrashFile("foo.eol"
		Map<String
		Map<String
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			while (walk.next()) {
				String path = walk.getPathString();
				if (".gitattributes".equals(path)) {
					continue;
				}
				EolStreamType in = walk
						.getEolStreamType(OperationType.CHECKIN_OP);
				EolStreamType out = walk
						.getEolStreamType(OperationType.CHECKOUT_OP);
				inTypes.put(path
				outTypes.put(path
			}
		}
		assertEquals(""
		assertEquals(""
	}

	private String checkTypes(String prefix
		StringBuilder result = new StringBuilder();
		EolStreamType a = types.get("foo.crlf");
		EolStreamType b = types.get("foo.txt");
		report(result
		a = types.get("foo.nocrlf");
		b = types.get("foo.bin");
		report(result
		a = types.get("foo.input");
		b = types.get("foo.eol");
		report(result
		return result.toString();
	}

	private void report(StringBuilder result
			EolStreamType a
			EolStreamType b) {
		if (a == null || b == null || !a.equals(b)) {
			result.append(prefix).append(' ').append(label).append(": ")
					.append(toString(a)).append(" != ").append(toString(b))
					.append('\n');
		}
	}

	private String toString(EolStreamType type) {
		return type == null ? "null" : type.name();
	}
}
