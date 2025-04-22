                for (int i = 0; i < elements.length; i++) {
                    if (elements[i] instanceof IResource) {
                        IMemento elementMem = selectionMem
                                .createChild(TAG_ELEMENT);
                        elementMem.putString(TAG_PATH,
                                ((IResource) elements[i]).getFullPath()
                                        .toString());
