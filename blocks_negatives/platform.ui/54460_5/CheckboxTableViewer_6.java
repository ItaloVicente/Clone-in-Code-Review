                    l.checkStateChanged(event);
                }
            });
        }
    }

    @Override
	public boolean getChecked(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TableItem) {
            return ((TableItem) widget).getChecked();
        }
        return false;
    }

    /**
     * Returns a list of elements corresponding to checked table items in this
     * viewer.
     * <p>
     * This method is typically used when preserving the interesting
     * state of a viewer; <code>setCheckedElements</code> is used during the restore.
     * </p>
     *
     * @return the array of checked elements
     * @see #setCheckedElements
     */
