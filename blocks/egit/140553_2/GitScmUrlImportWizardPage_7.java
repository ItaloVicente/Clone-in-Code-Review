		    for (String param : params) {
				if (param.startsWith("tag=")) {//$NON-NLS-1$
				} else if (param.startsWith("version=")) {//$NON-NLS-1$
				} else if (!param.equals("")) { //$NON-NLS-1$
			    sb.append(";").append(param); //$NON-NLS-1$
			}
		    }
