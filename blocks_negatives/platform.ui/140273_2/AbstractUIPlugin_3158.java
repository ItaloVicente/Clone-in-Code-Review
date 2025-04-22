            String readWritePath = path
                    .append(FN_DIALOG_SETTINGS).toOSString();
            dialogSettings.save(readWritePath);
        } catch (IOException e) {
        } catch (IllegalStateException e) {
        }
    }

    /**
     * Saves this plug-in's preference store.
     * Any problems which arise are silently ignored.
     *
     * @see Plugin#savePluginPreferences()
     * @deprecated As of Eclipse 2.0, preferences exist for all plug-ins. The
     * equivalent of this method is <code>Plugin.savePluginPreferences</code>.
     * This method now calls <code>savePluginPreferences</code>, and exists only for
     * backwards compatibility.
     */
    @Deprecated
