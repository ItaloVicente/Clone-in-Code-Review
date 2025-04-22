		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		writeTrashFile(fileName, sb.toString());
		git.add().addFilepattern(fileName).call();
