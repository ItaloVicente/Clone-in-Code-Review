      } catch (ParseException e) {
        getLogger().warn(
            "Provided URI " + baseUri
                + " has an unparsable response...skipping", e);
      } catch (IOException e) {
        getLogger().warn(
            "Connection problems with URI " + baseUri + " ...skipping", e);
      }
      throw new ConfigurationException("Configuration for bucket "
          + bucketToFind + " was not found.");
