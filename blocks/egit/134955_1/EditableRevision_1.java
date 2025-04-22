						return storage;
					}
				};
			}
			return input;
		}
		return super.getDocumentKey(element);
	}

	@Override
	protected ISharedDocumentAdapter createSharedDocumentAdapter() {
		return new EditableRevisionSharedDocumentAdapter(this);
