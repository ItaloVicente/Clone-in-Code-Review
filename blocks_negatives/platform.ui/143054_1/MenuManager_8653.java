    					return null;
    				}
                };
            } else {
                overrides = parent.getOverrides();
            }
            super.setOverrides(overrides);
        }
        return overrides;
    }

    /**
     * Returns the parent contribution manager of this manger.
     *
     * @return the parent contribution manager
     * @since 2.0
     */
    public IContributionManager getParent() {
        return parent;
    }

    @Override
