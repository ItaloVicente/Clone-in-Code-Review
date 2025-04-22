				.setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		RevCommit last = git.commit().setMessage("fourth commit").setAuthor(
				author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().addRange(second.getId(),
				last.getId()).call();
