
			BusyIndicator.showWhile(widget.getDisplay(), () -> {
				if (tis != null) {
					for (int i1 = 0; i1 < tis.length; i1++) {
						if (tis[i1].getData() != null) {
							disassociate(tis[i1]);
							Assert.isTrue(tis[i1].getData() == null,

						}
						tis[i1].dispose();
