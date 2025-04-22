	private static Future<Object> writeAsync(final OutputStream stream
		final ExecutorService executor = Executors.newSingleThreadExecutor();

			public Object call() throws IOException {
				try {
					stream.write(data);
					return null;
				} finally {
					stream.close();
				}
			}
		});
	}

	private String[] listTarEntries(byte[] tarData) throws Exception {
		final List<String> l = new ArrayList<String>();
		final Process proc = spawnAssumingCommandPresent("tar"
		final BufferedReader reader = readFromProcess(proc);
		final OutputStream out = proc.getOutputStream();

		final Future<?> writing = writeAsync(out

		try {
			String line;
			while ((line = reader.readLine()) != null)
				l.add(line);

			return l.toArray(new String[l.size()]);
		} finally {
			writing.get();
			reader.close();
			proc.destroy();
		}
	}

