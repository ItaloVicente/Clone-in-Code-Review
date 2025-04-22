			} else {
				if (!bare) {
					String dStr = SystemReader.getInstance().getProperty(
					if (dStr == null)
					builder.setWorkTree(new File(dStr));
				}
