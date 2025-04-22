			String itemId = cci.getId();
			toolItem.setElementId(itemId == null ? command.getElementId() : itemId);
			return toolItem;
		}
		return null;
	}
