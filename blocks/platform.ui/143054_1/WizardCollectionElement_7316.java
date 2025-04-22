				return currentCategory.findChildCollection(searchPath.removeFirstSegments(1));
			}
		}

		return null;
	}

	public WizardCollectionElement findCategory(String id) {
