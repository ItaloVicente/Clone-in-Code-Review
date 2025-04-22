		}

		resourceType = new FileEditorMapping(newName, newExtension);
		TableItem item = newResourceTableItem(resourceType, i, true);
		resourceTypeTable.setFocus();
		resourceTypeTable.showItem(item);
		fillEditorTable();
	}

	@Override
