			CSSImportRule importRule = (CSSImportRule) rule;
			URL url = null;
			if (importRule.getHref().startsWith("platform")) {
				url = FileLocator.resolve(new URL(importRule.getHref()));
			} else {
				Path p = new Path(source.getURI());
				IPath trim = p.removeLastSegments(1);

				url = FileLocator.resolve(new URL(trim.addTrailingSeparator()
						.toString() + ((CSSImportRule) rule).getHref()));
				File testFile = new File(url.getFile());
				if (!testFile.exists()) {
					String path = getResourcesLocatorManager().resolve(
							(importRule).getHref());
					testFile = new File(new URL(path).getFile());
					if (testFile.exists()) {
						url = new URL(path);
					}
