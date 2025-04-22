	@Override
	public ProcessBuilder runInShell(String cmd
		List<String> argv = new ArrayList<String>(4 + args.length);
		argv.add("sh");
		argv.add("-c");
		argv.add(cmd + " \"$@\"");
		argv.add(cmd);
		argv.addAll(Arrays.asList(args));
		ProcessBuilder proc = new ProcessBuilder();
		proc.command(argv);
		return proc;
	}

