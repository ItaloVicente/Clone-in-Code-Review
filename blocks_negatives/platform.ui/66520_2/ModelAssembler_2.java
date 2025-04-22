			if (merged.size() > 0) {
				evalImports = true;
				addedElements.addAll(merged);
			} else {
				logger.debug("Nothing to merge for \"{0}\"", uri); //$NON-NLS-1$
			}
		}
