				for (int i = 0; i < entries.length; ++i) {
					String e = entries[i];
					File f = new File(dir
					if (f.isDirectory())
						entries[i] += '/';
				}
