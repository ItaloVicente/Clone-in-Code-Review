		private List<String> replaceTilde(List<String> values
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(toFile(value
			}
			return result;
		}

