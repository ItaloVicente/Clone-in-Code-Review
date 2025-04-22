			r.append(getTagger());
			r.append("\n");
		}

		Charset encoding = getEncoding();
		if (!References.isSameObject(encoding
			r.append("encoding ");
			r.append(encoding.name());
