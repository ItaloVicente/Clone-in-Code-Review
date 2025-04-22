                ArrayList<Control> children = new ArrayList<>(items.length);
                for (int i = 0; i < items.length; i++) {
                    if ((items[i].getControl() != null)
                            && (!items[i].getControl().isDisposed())) {
                        children.add(items[i].getControl());
                    }
                }
