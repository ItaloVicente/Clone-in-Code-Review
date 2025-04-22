			CountingVisitor counter = new CountingVisitor();
			for (IProject p : projects) {
				try {
					p.accept(counter);
				} catch (CoreException e) {
				}
			}
