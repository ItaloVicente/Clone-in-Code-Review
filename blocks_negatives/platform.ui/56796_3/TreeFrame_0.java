            if (elements[i] instanceof IAdaptable) {
                IPersistableElement persistable = ((IAdaptable) elements[i]).getAdapter(IPersistableElement.class);
                if (persistable != null) {
                    IMemento elementMem = memento.createChild(TAG_ELEMENT);
                    elementMem.putString(TAG_FACTORY_ID, persistable
                            .getFactoryId());
                    persistable.saveState(elementMem);
                }
