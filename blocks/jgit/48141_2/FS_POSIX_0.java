			if (searchPath(path
				String w = readPipe(userHome()
						new String[] { "bash"
						Charset.defaultCharset().name());
				if (w == null || w.length() == 0)
					return null;
				gitExe = new File(w);
				return resolveGrandparentFile(gitExe);
			}
