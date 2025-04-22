							break;
						}
					}
				}
			}
		}
		if (parser == null)
			parser = new DefaultSectionsParser();
		registryLoaded = true;
	}

	private void processParserElement(IConfigurationElement element) {
		try {
			parser = (IReadmeFileParser) element.createExecutableExtension(IReadmeConstants.ATT_CLASS);
		} catch (CoreException e) {
			System.out.println(MessageUtil.getString("Unable_to_create_file_parser") + e.getStatus().getMessage()); //$NON-NLS-1$
			parser = null;
		}
	}
