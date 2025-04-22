		BufferedReader reader = readFromProcess(proc);
		OutputStream out = proc.getOutputStream();
		Future<?> writing = writeAsync(out, tarData);

		try {
			String line;
			while ((line = reader.readLine()) != null)
				l.add(line);

			return l.toArray(new String[l.size()]);
		} finally {
			writing.get();
			reader.close();
			proc.destroy();
