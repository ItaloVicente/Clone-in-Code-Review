			List<MApplicationElement> merged = processModelFragment(fragment, contributorURI, checkExist);
			if (merged.size() > 0) {
				evalImports = true;
				addedElements.addAll(merged);
			} else {
				logger.debug("Nothing to merge for fragment \"{0}\" of \"{1}\"", ce.getAttribute("uri"), //$NON-NLS-1$ //$NON-NLS-2$
						ce.getContributor().getName());
			}
