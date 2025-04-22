			BusyIndicator.showWhile(widget.getDisplay(), new Runnable() {
				@Override
				public void run() {
					if (tis != null) {
						for (int i = 0; i < tis.length; i++) {
							if (tis[i].getData() != null) {
								disassociate(tis[i]);
								Assert.isTrue(tis[i].getData() == null,

							}
							tis[i].dispose();
