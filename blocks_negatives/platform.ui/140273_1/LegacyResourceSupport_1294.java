	         }


            Object r = getAdaptedContributorResource(object);
            if (r != null) {
            	return Platform.getAdapterManager().getAdapter(r, resourceMappingClass);
            }

            return null;
        }
        return Platform.getAdapterManager().getAdapter(object, resourceMappingClass);
    }

    /**
     * Adapts a selection to the given objectClass considering the Legacy resource
     * support. Non resource objectClasses are adapted using the <code>IAdapterManager</code>
     * and this may load the plug-in that contributes the adapter factory.
     * <p>
     * The returned selection will only contain elements successfully adapted.
     * </p>
     * @param selection the selection to adapt
     * @param objectClass the class name to adapt the selection to
     * @return an adapted selection
     *
     * @since 3.1
     */
    public static IStructuredSelection adaptSelection(IStructuredSelection selection, String objectClass) {
