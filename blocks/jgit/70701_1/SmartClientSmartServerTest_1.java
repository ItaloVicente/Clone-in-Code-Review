	@Test
	public void testFetch_RefsUnreadableOnUpload() throws Exception {
		AppServer noRefServer = new AppServer();
		try {
			final String repoName = "refs-unreadable";
			RefsUnreadableInMemoryRepository badRefsRepo = new RefsUnreadableInMemoryRepository(
					new DfsRepositoryDescription(repoName));
			final TestRepository<Repository> repo = new TestRepository<Repository>(
					badRefsRepo);

			ServletContextHandler app = noRefServer.addContext("/git");
			GitServlet gs = new GitServlet();
			gs.setRepositoryResolver(new TestRepoResolver(repo
			app.addServlet(new ServletHolder(gs)
			noRefServer.setUp();

			RevBlob A2_txt = repo.blob("A2");
			RevCommit A2 = repo.commit().add("A2_txt"
			RevCommit B2 = repo.commit().parent(A2).add("A2_txt"
					.add("B2"
			repo.update(master

			URIish badRefsURI = new URIish(noRefServer.getURI()
					.resolve(app.getContextPath() + "/" + repoName).toString());

			Repository dst = createBareRepository();
			try (Transport t = Transport.open(dst
					FetchConnection c = t.openFetch()) {
				badRefsRepo.startFailing();
				badRefsRepo.getRefDatabase().refresh();
				c.fetch(NullProgressMonitor.INSTANCE
						Collections.singleton(c.getRef(master))
						Collections.<ObjectId> emptySet());
				fail("Successfully served ref with value " + c.getRef(master));
			} catch (TransportException err) {
				assertEquals("internal server error"
			}
		} finally {
			noRefServer.tearDown();
		}
	}

