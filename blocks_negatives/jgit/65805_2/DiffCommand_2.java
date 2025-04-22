			List<DiffEntry> result = diffFmt.scan(oldTree, newTree);
			if (showNameAndStatusOnly)
				return result;
			else {
				if (contextLines >= 0)
					diffFmt.setContext(contextLines);
				if (destinationPrefix != null)
					diffFmt.setNewPrefix(destinationPrefix);
				if (sourcePrefix != null)
					diffFmt.setOldPrefix(sourcePrefix);
				diffFmt.format(result);
				diffFmt.flush();
				return result;
