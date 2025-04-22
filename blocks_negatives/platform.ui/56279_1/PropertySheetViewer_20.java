                Point pt = new Point(event.x, event.y);
                TreeItem item = tree.getItem(pt);
                if (item != null) {
                    handleSelect(item);
                }
            }
        });

        tree.addTreeListener(new TreeListener() {
            @Override
