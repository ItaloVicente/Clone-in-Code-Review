			while (tw.next()) {
				if (tw.getPathString().equals("a")) {
					a = tw.getObjectId(0);
					tw.enterSubtree();
					while (tw.next()) {
						if (tw.getPathString().equals("a/c")) {
							aSlashC = tw.getObjectId(0);
							break;
