                                if (event.detail == DND.DROP_MOVE)
                                        label.setText("");
                        }
                });

                DropTarget target = new DropTarget(label, operations);
                target.setTransfer(types);
                target.addDropListener(new DropTargetAdapter() {
                        @Override
