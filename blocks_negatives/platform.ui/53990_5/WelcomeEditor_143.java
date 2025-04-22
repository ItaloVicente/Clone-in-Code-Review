                    }
                    if (previousTabAbortTraversal) {
                        previousTabAbortTraversal = false;
                        return;
                    }
                    StyleRange previousLink = findPreviousLink(text);
                    if (previousLink == null) {
                        if (text.getSelection().x == 0) {
                            StyledText previousText = previousText(text);
                            previousText.setSelection(previousText
                                    .getCharCount());
                            previousLink = findPreviousLink(previousText);
                            if (previousLink == null) {
								focusOn(previousText, 0);
							} else {
                                focusOn(previousText, previousText
                                        .getSelection().x);
                                previousText
                                        .setSelectionRange(previousLink.start,
                                                previousLink.length);
                            }
                        } else {
                            focusOn(text, 0);
                        }
                    } else {
                        focusOn(text, text.getSelection().x);
                        text.setSelectionRange(previousLink.start,
                                previousLink.length);
                    }
                    e.detail = SWT.TRAVERSE_NONE;
                    e.doit = true;
                    break;
                default:
                    break;
                }
            }
        });
