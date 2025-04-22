	@Test(expected = IllegalArgumentException.class)
	public void testInvalidScmUri() throws Exception {
		URI.create("scm:git:git://git.eclipse.org/gitroot/platform/eclipse.platform.team.git;path=\"bundles/org.eclipse.team.core\"");
	}

	@Test
	public void testScmUri1() throws Exception {
		ScmUrlImportDescription description = new ScmUrlImportDescription(
				"scm:git:git://git.eclipse.org/gitroot/platform/eclipse.platform.team.git;path=\"bundles/org.eclipse.team.core\"",
				null);
		URI uri = description.getUri();
		GitURI gitUri = GitURI.fromUri(uri);
		assertEquals("bundles/org.eclipse.team.core", gitUri.getPath()
				.toString());
		URIish uriish = new URIish(
				"git://git.eclipse.org/gitroot/platform/eclipse.platform.team.git");
		assertEquals(uriish, gitUri.getRepository());
		assertEquals(Constants.MASTER, gitUri.getTag());

		String refString = new GitProjectSetCapability().asReference(uri,
				"org.eclipse.team.core");
		assertEquals(
				"1.0,git://git.eclipse.org/gitroot/platform/eclipse.platform.team.git,master,bundles/org.eclipse.team.core",
				refString);
	}

	@Test
	public void testScmUri2() throws Exception {
		ScmUrlImportDescription description = new ScmUrlImportDescription(
				"scm:git:git://git.eclipse.org/gitroot/platform/eclipse.platform.ui.git;path=\"bundles/org.eclipse.jface\";tag=v20111107-2125",
				null);
		URI uri = description.getUri();
		GitURI gitUri = GitURI.fromUri(uri);
		assertEquals("bundles/org.eclipse.jface", gitUri.getPath().toString());
		URIish uriish = new URIish(
				"git://git.eclipse.org/gitroot/platform/eclipse.platform.ui.git");
		assertEquals(uriish, gitUri.getRepository());
		assertEquals("v20111107-2125", gitUri.getTag());

		String refString = new GitProjectSetCapability().asReference(uri,
				"org.eclipse.jface");
		assertEquals(
				"1.0,git://git.eclipse.org/gitroot/platform/eclipse.platform.ui.git,v20111107-2125,bundles/org.eclipse.jface",
				refString);
	}

	@Test
	public void testScmUri3() throws Exception {
		ScmUrlImportDescription description = new ScmUrlImportDescription(
				"scm:git:git://git.eclipse.org/gitroot/equinox/rt.equinox.bundles.git;path=\"bundles/org.eclipse.equinox.http.jetty6\";project=\"org.eclipse.equinox.http.jetty\";tag=v20111010-1614",
				null);
		URI uri = description.getUri();
		GitURI gitUri = GitURI.fromUri(uri);
		assertEquals("bundles/org.eclipse.equinox.http.jetty6", gitUri
				.getPath().toString());
		URIish uriish = new URIish(
				"git://git.eclipse.org/gitroot/equinox/rt.equinox.bundles.git");
		assertEquals(uriish, gitUri.getRepository());
		assertEquals("v20111010-1614", gitUri.getTag());
		assertEquals("org.eclipse.equinox.http.jetty", gitUri.getProjectName());

		String refString = new GitProjectSetCapability().asReference(uri,
				"org.eclipse.equinox.http.jetty");
		assertEquals(
				"1.0,git://git.eclipse.org/gitroot/equinox/rt.equinox.bundles.git,v20111010-1614,bundles/org.eclipse.equinox.http.jetty6",
				refString);
	}

