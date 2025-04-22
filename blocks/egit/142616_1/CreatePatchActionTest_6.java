				try {
					value[0] = (String) clp
							.getContents(TextTransfer.getInstance());
				} finally {
					clp.dispose();
				}
