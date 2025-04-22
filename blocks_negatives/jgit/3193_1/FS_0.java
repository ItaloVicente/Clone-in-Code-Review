			final Process p = Runtime.getRuntime().exec(command, null, dir);
			final BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream(), encoding));
			String r = null;
			try {
				r = lineRead.readLine();
			} finally {
				p.getOutputStream().close();
				p.getErrorStream().close();
				lineRead.close();
			}
