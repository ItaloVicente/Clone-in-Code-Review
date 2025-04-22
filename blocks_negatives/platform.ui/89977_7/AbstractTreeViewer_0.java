			BusyIndicator.showWhile(widget.getDisplay(), () -> {
				if (items != null) {
					for (Item item : items) {
						if (item.getData() != null) {
							disassociate(item);
							Assert.isTrue(item.getData() == null,
