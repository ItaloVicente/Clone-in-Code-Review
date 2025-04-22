package org.eclipse.jgit.gitrepo;

import static org.junit.Assert.assertTrue;

import java.io.StringBufferInputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ManifestParserTest {

	@Test
	public void testManifestParser() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		Set<String> results = new HashSet<String>();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append("foo")
			.append("\" groups=\"a
			.append("<project path=\"bar\" name=\"")
			.append("bar")
			.append("\" groups=\"notdefault\" />")
			.append("<project path=\"foo/a\" name=\"")
			.append("a")
			.append("\" groups=\"a\" />")
			.append("<project path=\"b\" name=\"")
			.append("b")
			.append("\" groups=\"b\" />")
			.append("</manifest>");

		ManifestParser parser = new ManifestParser(
				null
		parser.read(new StringBufferInputStream(xmlContent.toString()));
		results.clear();
		results.add("foo");
		results.add("bar");
		results.add("foo/a");
		results.add("b");
		for (RepoProject proj : parser.getProjects()) {
			String msg = String.format(
					"project \"%s\" should be included in unfiltered projects"
					proj.path);
			assertTrue(msg
			results.remove(proj.path);
		}
		assertTrue(
				"Unfiltered projects shouldn't contain any unexpected results"
				results.isEmpty());
		results.clear();
		results.add("foo");
		results.add("b");
		for (RepoProject proj : parser.getFilteredProjects()) {
			String msg = String.format(
					"project \"%s\" should be included in filtered projects"
					proj.path);
			assertTrue(msg
			results.remove(proj.path);
		}
		assertTrue(
				"Filtered projects shouldn't contain any unexpected results"
				results.isEmpty());
	}
}
