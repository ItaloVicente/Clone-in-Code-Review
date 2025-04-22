			stringBuffer.append(id);
			stringBuffer.append(',');
			stringBuffer.append(name);
			stringBuffer.append(',');
			stringBuffer.append(description);
			stringBuffer.append(',');
			stringBuffer.append(parentId);
			stringBuffer.append(',');
			stringBuffer.append(defined);
			stringBuffer.append(')');
			string = stringBuffer.toString();
		}
		return string;
	}

	@Override
