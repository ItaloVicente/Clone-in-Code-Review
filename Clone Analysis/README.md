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

### 🧩 Installing and Configuring Siamese

To properly configure **Siamese** for clone detection, follow the steps below:

1. into the `siamese/` directory located at the root of this project:

   ```bash
   git clone https://github.com/UCL-CREST/Siamese.git siamese
   ```

2. Inside the `siamese/` directory, rename the default configuration file
   
   ```bash
   mv siamese/config.properties siamese/index-config.properties
   ```

3. Create a copy for the search configuration
   
   ```bash
   cp siamese/index-config.properties siamese/search-config.properties
   ```

4. In both config files, update the path to your Elasticsearch instance:
(Recommended: keep Elasticsearch inside the same `siamese/` directory)
In both `index-config.properties` and `search-config.properties`:
   elasticsearchLoc=siamese/elasticsearch-7.9.2

5. Set the correct command mode in each file
In `index-config.properties`:
   command=index

In `search-config.properties`:
   command=search

### Siamese Configuration

Siamese is already included in this repository and utilizes Elasticsearch for its operations. Before execution, configure the `settings.ini` file to define the full paths and necessary parameters.

## Running the Scripts

The scripts must be executed in sequential order from **1 to 9**, structured across four key phases:
Phase 1: Data Extraction available in repo CROP Update

### 🔍 Phase 2: Clone Extraction and Processing

Scripts 1 to 6 are responsible for this stage:

1. **Generate diff files** for each revision of the project specified in `settings.ini`.  
   ➤ Output: `diff/` directory

2. **Extract added code blocks**, using a minimum size threshold defined in the `min_clone` parameter.  
   ➤ Output: `blocks/` directory

3. **Detect clones using Siamese**, performing indexing and searching.  
   ➤ Output: `search_results/` directory

4. **Rename blocks**, assigning file-based identifiers and resetting block IDs per revision to track recurrence throughout the review.  
   ➤ Output: `blocks/` directory

5. **Generate negative blocks**, which represent removed code to help identify false positives due to code movements.  
   ➤ Output: `blocks_negatives/` directory

6. **Context classification** of clones. Produces a CSV (`typeclones/project(original).csv`) with the following columns:
   - `review_number`: Review ID
   - `revision_number`: Revision number
   - `arquivo_positivo`: Block source file
   - `potencial_deslocamento`: (1 or 0) for detected code movements
   - `potencial_teste`: (1 or 0) for test file clones
   - `arquivo_diferente`: (1 or 0) for clones copied to other files

---

### 👀 Phase 3: Manual Validation

Before beginning this phase, run `check_clones_that_no_repeat.py` to filter and de-duplicate individual clones.  
➤ Output: `typeclones/project_equals_reviews.json`.

Next, run `random_amostragem.py`, where you can define sample and pilot sizes per project.  
➤ Output: XLSX spreadsheet located in the `amostra_excel/` folder.

---

### 🔄 Phase 4: Lifecycle Analysis of Clones

Scripts 7 to 9 handle the lifecycle computation:

7. **Normalize block identifiers** across revisions within a single review. Ensures consistency of block IDs even if block position or count changes.  
   ➤ Output: Overwrites file in `typeclones/project.csv`

8. **Review classification** based on the presence and type of clones:
   - No clones
   - Irrelevant clones
   - Relevant clones  
   ➤ Output: `separate_review/project.csv`

9. **Compute duration and distance** for recurring clones in multiple-revision reviews.  
   ➤ Output:  
   - `classification/project.csv`: Final categorization of reviews  
   - `new_categorization/project.csv`: Duration and distance metrics

---

## 📊 Additional Analysis Scripts

These scripts are located in the `extras/` directory and must be moved into the main `Clone-in-Code-Review/` folder for execution.

- `count_new_categorization`: Categorizes reviews as unique or multiple and provides subclass counts.
- `count_clone_in_projects`: Counts total clones found per project.
- `count_data`: Creates a Venn diagram based on initial clone type classification.
- `count_reviews_multiplas`: Counts how many reviews are classified as multiple.
- `made_box_plot`: Generates box plots for distance and duration analysis.
- `count_review_analysis`: Provides a compact summary with subcategories in a single CSV file.

---

## 📈 Results from the Final Dataset

1. The `type_clones/` directory contains all code blocks identified by Siamese along with their classification.
2. The `new_categorization/` directory holds data about the lifecycle of clones (duration and distance).
3. The `classification/` directory separates reviews into "unique" or "multiple", and tags whether they contain relevant clones.
4. The `separate_reviews/` directory provides the same review classification (no clones, irrelevant, or relevant clones).
5. The `review_analysis/` file offers lifecycle insights without distance and duration, combining unique and multiple reviews into a single summary.

---
