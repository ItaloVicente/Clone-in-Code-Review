                        valueChanged();
                    }
                });
                break;
            default:
                Assert.isTrue(false, "Unknown validate strategy");//$NON-NLS-1$
            }
            textField.addDisposeListener(event -> textField = null);
                textField.setTextLimit(textLimit);
            }
        } else {
            checkParent(textField, parent);
        }
        return textField;
    }

    /**
     * Returns whether an empty string is a valid value.
     *
     * @return <code>true</code> if an empty string is a valid value, and
     *  <code>false</code> if an empty string is invalid
     * @see #setEmptyStringAllowed
     */
    public boolean isEmptyStringAllowed() {
        return emptyStringAllowed;
    }

    @Override
