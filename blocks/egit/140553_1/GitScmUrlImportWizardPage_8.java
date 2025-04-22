		    for (String param : params) {
			if (param.startsWith("tag=")) {
			} else if (param.startsWith("version=")) {
			} else if (param != null && !param.equals("")) {
			    sb.append(";").append(param); //$NON-NLS-1$
			}
		    }
