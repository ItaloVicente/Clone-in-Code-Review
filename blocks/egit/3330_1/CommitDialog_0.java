			String encoding;
			try {
				encoding = commitItem.file.getCharset();
			} catch (CoreException e1) {
				encoding = null;
			}

			ITypedElement base = new FileRevisionTypedElement(baseFile, encoding);
			ITypedElement next = new FileRevisionTypedElement(nextFile, encoding);
