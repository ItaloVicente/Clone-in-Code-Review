			assertEquals(null,
					git2.getRepository().resolve("refs/heads/upstreambranch"));
			assertEquals(null,
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null,
					git2.getRepository().resolve("refs/heads/master"));
