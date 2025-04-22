            if (elements[i] instanceof IAdaptable) {
                IPersistableElement persistable = (IPersistableElement) ((IAdaptable) elements[i])
                        .getAdapter(IPersistableElement.class);
                if (persistable != null) {
                    IMemento elementMem = memento.createChild(TAG_ELEMENT);
                    elementMem.putString(TAG_FACTORY_ID, persistable
                            .getFactoryId());
                    persistable.saveState(elementMem);
                }
