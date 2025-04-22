		case SYMBOLICREFS:
			try {
				for (Ref refEntry : repo.getRefDatabase().getRefs(
						RefDatabase.ALL).values()) {
					if (refEntry.isSymbolic())
						return true;
				}
			} catch (IOException e) {
				return true;
			}
			return false;
