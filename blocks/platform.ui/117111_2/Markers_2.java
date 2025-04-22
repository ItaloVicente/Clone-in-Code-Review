		} catch (IllegalArgumentException e) {
			StringBuilder err = new StringBuilder("Bug 371586: broken comparator. "); //$NON-NLS-1$
			if (lastCategory != null) {
				err.append(lastCategory);
			} else {
				err.append(markerComparator.getCategory());
			}
			err.append(", fields: "); //$NON-NLS-1$
			err.append(Arrays.toString(markerComparator.getFields()));
			IDEWorkbenchPlugin.log(err.toString(), e);
			return false;
