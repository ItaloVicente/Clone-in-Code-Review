			if (LOG.isDebugEnabled()) {
				LOG.debug(MessageFormat.format(warnTmpl,
						p.getPackFile().getAbsolutePath()), e);
			} else {
				LOG.warn(MessageFormat.format(warnTmpl,
						p.getPackFile().getAbsolutePath()));
			}
