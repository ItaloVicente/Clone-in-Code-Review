			if (debug)
				System.err.println("readpipe " + Arrays.asList(command) + "
					System.err.println("(ignoring remaing output:");
				}
				String l;
				while ((l = lineRead.readLine()) != null) {
					if (debug)
						System.err.println(l);
				}
