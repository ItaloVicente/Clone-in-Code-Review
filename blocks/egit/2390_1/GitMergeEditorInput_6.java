				FileTreeIterator fit = tw.getTree(fileTreeIndex,
						FileTreeIterator.class);
				if (fit == null || fit.isEntryIgnored())
					continue;
				boolean modified = rt != null
						&& !fit.getEntryObjectId()
								.equals(rt.getEntryObjectId());
