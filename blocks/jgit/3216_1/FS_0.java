				p = Runtime.getRuntime().exec(command
				TimeoutInputStream in = new TimeoutInputStream(
						p.getInputStream()
				in.setTimeout(10000);
				BufferedReader lineRead = new BufferedReader(
						new InputStreamReader(in
				String r = lineRead.readLine();
				if (r != null && r.length() > 0)
					return r;
			} finally {
				timer.terminate();
				p.getOutputStream().close();
				p.getErrorStream().close();
