			String srcId = sourcePart == null ? null : sourcePart.getSite().getId();
			ArrayList<?> pagePartIds = page.getShowInPartIds();
			for (Object pagePartId : pagePartIds) {
				if (!pagePartId.equals(srcId)) {
					targetIds.add(pagePartId);
				}
			}
