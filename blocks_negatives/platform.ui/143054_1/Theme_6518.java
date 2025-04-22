                    } catch (DataFormatException e) {
                    }
                }

                /**
                 * Process all fonts that default to the given ID.
                 *
                 * @param key the font ID
                 * @param fd the new FontData for defaulted fonts
                 */
                private void processDefaultsTo(String key, FontData[] fd) {
