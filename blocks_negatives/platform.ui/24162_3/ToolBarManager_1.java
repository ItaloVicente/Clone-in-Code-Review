                        if (dest != null && src.equals(dest)) {
                            srcIx++;
                            destIx++;
                            continue;
                        }

                        if (dest != null && dest.isSeparator()
                                && src.isSeparator()) {
                            mi[srcIx].setData(src);
                            srcIx++;
                            destIx++;
                            continue;
                        }

                        int start = toolBar.getItemCount();
                        src.fill(toolBar, destIx);
                        int newItems = toolBar.getItemCount() - start;
                        for (int i = 0; i < newItems; i++) {
                            ToolItem item = toolBar.getItem(destIx++);
                            item.setData(src);
                        }
                    }

                    for (int i = mi.length; --i >= srcIx;) {
                        ToolItem item = mi[i];
                        if (!item.isDisposed()) {
                            Control ctrl = item.getControl();
                            if (ctrl != null) {
                                item.setControl(null);
                                ctrl.dispose();
                            }
                            item.dispose();
                        }
                    }

                    setDirty(false);

                } finally {
                    if (useRedraw) {
                        toolBar.setRedraw(true);
                    }
                }
