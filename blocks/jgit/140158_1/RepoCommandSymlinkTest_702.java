package org.eclipse.jgit.gitrepo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ManifestParserTest {

	@Test
	public void testManifestParser() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		Set<String> results = new HashSet<>();
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
		parser.read(new ByteArrayInputStream(xmlContent.toString().getBytes(UTF_8)));
		results.clear();
		results.add("foo");
		results.add("bar");
		results.add("foo/a");
		results.add("b");
		for (RepoProject proj : parser.getProjects()) {
			String msg = String.format(
					"project \"%s\" should be included in unfiltered projects"
					proj.getPath());
			assertTrue(msg
			results.remove(proj.getPath());
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
					proj.getPath());
			assertTrue(msg
			results.remove(proj.getPath());
		}
		assertTrue(
				"Filtered projects shouldn't contain any unexpected results"
				results.isEmpty());
	}

	@Test
	public void testManifestParserWithMissingFetchOnRemote() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append("foo")
				.append("\" groups=\"a
				.append("<project path=\"bar\" name=\"").append("bar")
				.append("\" groups=\"notdefault\" />")
				.append("<project path=\"foo/a\" name=\"").append("a")
				.append("\" groups=\"a\" />")
				.append("<project path=\"b\" name=\"").append("b")
				.append("\" groups=\"b\" />").append("</manifest>");

		ManifestParser parser = new ManifestParser(null
				baseUrl
		try {
			parser.read(new ByteArrayInputStream(
					xmlContent.toString().getBytes(UTF_8)));
			fail("ManifestParser did not throw exception for missing fetch");
		} catch (IOException e) {
			assertTrue(e.getCause() instanceof SAXException);
			assertTrue(e.getCause().getMessage()
					.contains("is missing fetch attribute"));
		}
	}

	@Test
	public void testRemoveProject() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"foo\" />")
				.append("<project path=\"bar\" name=\"bar\" />")
				.append("<remove-project name=\"foo\" />")
				.append("<project path=\"foo\" name=\"baz\" />")
				.append("</manifest>");

		ManifestParser parser = new ManifestParser(null
		parser.read(new ByteArrayInputStream(
				xmlContent.toString().getBytes(UTF_8)));

		assertEquals(Stream.of("bar"
				parser.getProjects().stream().map(RepoProject::getName)
						.collect(Collectors.toSet()));
	}

	void testNormalize(String in
		URI got = ManifestParser.normalizeEmptyPath(URI.create(in));
		if (!got.toString().equals(want)) {
			fail(String.format("normalize(%s) = %s want %s"
		}
	}

	@Test
	public void testNormalizeEmptyPath() {
		testNormalize(""
		testNormalize("a/b"
	}
}
