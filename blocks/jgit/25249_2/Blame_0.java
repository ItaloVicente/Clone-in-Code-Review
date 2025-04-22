			if (time) {
				outs = new BufferedOutputStream(new FileOutputStream(
						"blame.jgit"));
				outw = new ThrowingPrintWriter(new OutputStreamWriter(outs
						"UTF-8"));
			}

