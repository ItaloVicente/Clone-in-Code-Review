                updateLabels();
            }
        });
    }

    private void updateLabels() {
        titleLabel.setText(getTitle());
        nameLabel.setText(getPartName());
        cdLabel.setText(getContentDescription());
    }

    @Override
