		}
		catch (Exception ex) {
			throw new JXPathException(
				"Cannot modify property: "
					+ (bean == null ? "null" : bean.getClass().getName())
					+ "."
					+ pd.getName(),
				ex);
		}
