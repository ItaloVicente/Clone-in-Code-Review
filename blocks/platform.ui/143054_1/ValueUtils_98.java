			try {
				return ((List<Object>)bean.eGet(pd)).get(index);
			}
			catch (IndexOutOfBoundsException ex) {
				return null;
			}
			catch (Throwable ex) {
				throw new JXPathException(
					"Cannot access property: " + pd.getName(),
					ex);
			}
		}
