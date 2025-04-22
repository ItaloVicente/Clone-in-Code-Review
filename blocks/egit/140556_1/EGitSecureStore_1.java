				if (null != s)
					switch (s) {
					case "http": //$NON-NLS-1$
				    storedURI = storedURI.setPort(80);
				    break;
					case "https": //$NON-NLS-1$
				    storedURI = storedURI.setPort(443);
				    break;
					case "ssh": //$NON-NLS-1$
					case "sftp": //$NON-NLS-1$
				    storedURI = storedURI.setPort(22);
				    break;
					case "ftp": //$NON-NLS-1$
				    storedURI = storedURI.setPort(21);
				    break;
			    	default:
				    break;
			    }
