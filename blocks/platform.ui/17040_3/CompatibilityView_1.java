
			String partId = part.getElementId();
			int colonIndex = partId.indexOf(':');
			String descId = colonIndex == -1 ? partId : partId.substring(0, colonIndex);
			toolbar.setElementId(descId);

