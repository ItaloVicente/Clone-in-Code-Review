			URIish u = new URIish(uri);
			repository = init(u);
			FetchResult result = fetch(repository, u);
			if (!noCheckout)
				checkout(repository, result);
			return new Git(repository, true);
