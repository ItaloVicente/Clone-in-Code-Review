            }
        }

        super.preservingSelection(updateCode);

        children = getTable().getItems();
        for (int i = 0; i < children.length; i++) {
            TableItem item = children[i];
            Object data = item.getData();
            if (data != null) {
                item.setChecked(checked.containsKey(data));
                item.setGrayed(grayed.containsKey(data));
            }
        }
    }

    @Override
