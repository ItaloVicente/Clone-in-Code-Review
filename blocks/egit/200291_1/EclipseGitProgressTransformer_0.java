				if (!StringUtils.isEmptyOrNull(msg)) {
					m.append(msg).append(", "); //$NON-NLS-1$
				}
				m.append(cmp);
				appendDuration(m, elapsedTime());
				root.subTask(m.toString());
