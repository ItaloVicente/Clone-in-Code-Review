			for (;;) {
				try {
					if (p.waitFor() == 0 && r != null && r.length() > 0)
						return r;
					break;
				} catch (InterruptedException ie) {
