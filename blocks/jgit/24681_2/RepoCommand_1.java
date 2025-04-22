				if (currentProject == null)
					throw new SAXException(RepoText.get().invalidManifest);
				currentProject.addCopyFile(new CopyFile(
							currentProject.path
							attributes.getValue("src")
			}
		}

		@Override
		public void endElement(
				String uri
				String localName
				String qName) throws SAXException {
				projects.add(currentProject);
				currentProject = null;
