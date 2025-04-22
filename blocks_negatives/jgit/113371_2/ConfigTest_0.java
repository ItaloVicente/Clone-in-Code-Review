		String fooMore = "[foo]\nmore=bar\n";
		Files.write(more.toPath(), fooMore.getBytes());

		String otherMore = "[other]\nmore=bar\n";
		Files.write(other.toPath(), otherMore.getBytes());

