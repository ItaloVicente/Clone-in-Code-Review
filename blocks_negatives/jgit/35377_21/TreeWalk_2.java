					attributesFile = fs.resolve(fs.userHome(),
							path.substring(2));
				else
					attributesFile = fs.resolve(null, path);
				loadRulesFromFile(r, attributesFile);
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

	/** Magic type indicating there may be rules for the top level. */
	private static class InfoAttributesNode extends AttributesNode {
		final Repository repository;

		InfoAttributesNode(Repository repository) {
			this.repository = repository;
		}

		AttributesNode load() throws IOException {
			AttributesNode r = new AttributesNode();

			FS fs = repository.getFS();

			File attributes = fs.resolve(repository.getDirectory(),
			loadRulesFromFile(r, attributes);

			return r.getRules().isEmpty() ? null : r;
		}

	}

	private static void loadRulesFromFile(AttributesNode r, File attrs)
			throws FileNotFoundException, IOException {
		if (attrs.exists()) {
			FileInputStream in = new FileInputStream(attrs);
			try {
				r.parse(in);
			} finally {
				in.close();
			}
		}
	}
