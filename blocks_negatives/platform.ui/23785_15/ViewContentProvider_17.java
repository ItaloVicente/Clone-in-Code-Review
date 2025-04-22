                    filtered.add(o);
                }
                return removeIntroView(filtered).toArray();
            }
        }

        return new Object[0];
    }

    /**
	 * Removes the temporary intro view from the list so that it cannot be
	 * activated except through the introduction command.
	 *
	 * @param list
	 *            the list of view descriptors
	 * @return the modified list.
	 * @since 3.0
	 */
    private ArrayList<Object> removeIntroView(ArrayList<Object> list) {
        for (Iterator<Object> i = list.iterator(); i.hasNext();) {
            IViewDescriptor view = (IViewDescriptor) i.next();
            if (view.getId().equals(IIntroConstants.INTRO_VIEW_ID)) {
                i.remove();
            }
        }
        return list;
    }

    @Override
	public Object[] getElements(Object element) {
        return getChildren(element);
    }
