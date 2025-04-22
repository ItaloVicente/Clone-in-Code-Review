					fireContentChanged();
					RepositoryMapping mapping = RepositoryMapping.getMapping(path);
					if (mapping != null)
						mapping.getRepository().fireEvent(new IndexChangedEvent());
					if (out != null)
						try {
							out.close();
						} catch (IOException ex) {
						}
