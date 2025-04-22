				if (currentProject == null)
					throw new SAXException(RepoText.get().invalidManifest);
				currentProject.addCopyFile(new CopyFile(
							currentProject.path
							attributes.getValue("src")
							attributes.getValue("dest")));
			}
		}

		@Override
		public void endElement(
				String uri
				String localName
				String qName) throws SAXException {
			if ("project".equals(qName)) {
				projects.add(currentProject);
				currentProject = null;
