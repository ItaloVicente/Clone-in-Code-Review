				.setCommitter(committer).setAllowEmpty(true).call();
		git.commit().setMessage("third commit").setAuthor(author)
				.setAllowEmpty(true).call();
		RevCommit last = git.commit().setMessage("fourth commit")
				.setAuthor(author).setCommitter(committer).setAllowEmpty(true)
				.call();
		Iterable<RevCommit> commits = git.log()
				.addRange(second.getId()
