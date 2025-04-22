		Transport transport = null;
		FetchConnection fc = null;
		try {
			if (repo != null)
				transport = Transport.open(repo, remote);
			else
				transport = Transport.open(new URIish(remote));
