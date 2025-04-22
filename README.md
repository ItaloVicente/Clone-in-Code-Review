# README - Clone Detection Pipeline

## Prerequisites

Before running the scripts in this repository, ensure that your machine has the following installed:

- **Java 8** (Required by the Siamese tool)
- **Elasticsearch 2.2.0** (Required for indexing and searching clones)

### Installing Elasticsearch

To install Elasticsearch 2.2.0, follow these steps:

```bash
mkdir ~/siamese
cd ~/siamese
wget https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.2.0/elasticsearch-2.2.0.tar.gz
tar -xvf elasticsearch-2.2.0.tar.gz
rm elasticsearch-2.2.0.tar.gz
```

### Configuring Elasticsearch

Modify the configuration file `config/elasticsearch.yml`:

```bash
cd elasticsearch-2.2.0
vim config/elasticsearch.yml
```

Add the following lines at the end of the file, then save and exit:

```yaml
cluster.name: stackoverflow
index.query.bool.max_clause_count: 4096
```

Finally, move the elasticsearch-2.2.0 folder into the siamese/ directory.

### Siamese Configuration

Siamese is already included in this repository and utilizes Elasticsearch for its operations. Before execution, configure the `settings.ini` file to define the full paths and necessary parameters.

## Running the Scripts

The scripts must be executed in order from **1 to 12**. Below is an overview of each script:

1. **Generates diff files** for each revision of the project specified in `settings.ini`. Output: `diff/` folder.
2. **Extracts added code blocks**, considering a minimum size specified by the `min_clone` parameter. Output: `blocks/` folder.
3. **Uses Siamese for clone detection**, employing indexing and search functions. Output: `search_results/` folder.
   4-6. **Handle false positives** by renaming and comparing added blocks with removed blocks. A preliminary classification is then performed, identifying test clones, moved code, and copies within the same or different files.
   7-9. **Tracks clone persistence across revisions**, classifying whether a clone is recurrent, unique, and determining its lifespan within the code review process.
   10-12. **Analyze duration and distance metrics** for the detected clones.

## Additional Analysis Scripts

In addition to the core scripts, the following supplementary scripts generate further insights:

- **`count_new_categorization`**: Categorizes reviews as unique or multiple and provides subclassification counts.
- **`count_clone_in_projects`**: Counts the number of clones detected in each project.
- **`count_data`**: Generates a Venn diagram for the initial classification of projects.
- **`count_reviews_multiplas`**: Counts the number of reviews classified as uniquely multiple.
- **`made_box_plot`**: Generates box plots for duration and distance metrics.

##Results from final dataset

1. The `type_clones/` directory contains all code blocks identified as clones by the Siamese tool, along with their respective classifications.
2. The `new categoration/` directory provides information about the lifecycle of clones within both multiple and unique code reviews.
3. The `classification/` directory contains the classification of each review as either multiple or unique and indicates whether a relevant clone was found.

