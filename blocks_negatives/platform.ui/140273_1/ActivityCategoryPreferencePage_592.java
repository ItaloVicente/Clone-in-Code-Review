        /**
         * @param decorate
         */
        public CategoryLabelProvider(boolean decorate) {
            this.decorate = decorate;
            lockDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
                    PlatformUI.PLUGIN_ID, "icons/full/ovr16/lock_ovr.png"); //$NON-NLS-1$
        }
