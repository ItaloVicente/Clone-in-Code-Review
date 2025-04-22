		StringBuilder sb = new StringBuilder();
		for (String value: valueList){
			sb.append('[');
			sb.append(value);
			sb.append(']');
		}

		return sb.toString();
