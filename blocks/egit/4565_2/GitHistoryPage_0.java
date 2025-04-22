				final PlotWalk walk = new PlotWalk(input.getRepository());
				try {
					RevCommit unfilteredCommit = walk.parseCommit(c);
					for (RevCommit parent : unfilteredCommit.getParents())
						walk.parseBody(parent);
					commentViewer.setInput(unfilteredCommit);
					fileViewer.setInput(unfilteredCommit);
				} catch (IOException e) {
					commentViewer.setInput(c);
					fileViewer.setInput(c);
				} finally {
					walk.dispose();
				}
