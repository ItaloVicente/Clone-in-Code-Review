			} else {
				if (!bare) {
					String dStr = SystemReader.getInstance().getProperty(
					if (dStr == null)
						dStr = ".";
					builder.setWorkTree(new File(dStr));
				}
