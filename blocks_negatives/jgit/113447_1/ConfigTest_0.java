		String includeMore = "[include]\npath=" + more.toPath() + "\n";
		String includeOther = "path=" + other.toPath() + "\n";
		String fooPlus = fooBar + includeMore + includeOther;
		Files.write(config.toPath(), fooPlus.getBytes());

		String fooMore = "[foo]\nmore=bar\n";
		Files.write(more.toPath(), fooMore.getBytes());

		String otherMore = "[other]\nmore=bar\n";
		Files.write(other.toPath(), otherMore.getBytes());
