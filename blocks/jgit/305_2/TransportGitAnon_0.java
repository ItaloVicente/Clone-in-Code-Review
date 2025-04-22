				InputStream sIn = sock.getInputStream();
				OutputStream sOut = sock.getOutputStream();

				sIn = new BufferedInputStream(sIn);
				sOut = new BufferedOutputStream(sOut);

				init(sIn
