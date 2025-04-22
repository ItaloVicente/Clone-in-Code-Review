			try {
				if (FS.DETECTED.exists(exclude)) {
					FileInputStream in = new FileInputStream(exclude);
					try {
						r.parse(in);
					} finally {
						in.close();
					}
