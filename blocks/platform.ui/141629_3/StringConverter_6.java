            if (null == s) {
            	throw new DataFormatException("Unknown face name \"" + s + "\""); //$NON-NLS-2$//$NON-NLS-1$
            } else switch (s) {
            case BOLD_ITALIC:
            	style = SWT.BOLD | SWT.ITALIC;
            	break;
            case BOLD:
            	style = SWT.BOLD;
            	break;
            case ITALIC:
            	style = SWT.ITALIC;
            	break;
            case REGULAR:
            	style = SWT.NORMAL;
            	break;
            default:
            	throw new DataFormatException("Unknown face name \"" + s + "\""); //$NON-NLS-2$//$NON-NLS-1$
