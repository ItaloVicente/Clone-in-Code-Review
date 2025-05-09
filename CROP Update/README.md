# Code Review Open Platform
# Phase 1 CROP Update

----
This repository contain the scripts necessary to replicate the CROP dataset as described in the paper entitled "CROP: Linking Code Reviews to Source Code Changes" published on the 2018th edition of the Working Workshop on Mining Software Repositories ([MSR'18](https://conf.researchr.org/home/msr-2018))

----
## Usage

The scripts are written in python3 and need to be executed sequentially in the order they are numbered. All scripts are commented, so for more details of usage please check the code for each file.

The `settings.ini` files are used to configure the scripts for which project the script will be executed. These are the scripts:

1. Reviews' Details Crawler:

> This script downloads the JSON files containing the details of every review in a certain community (Eclipse or Couchbase). The reviews JSON files are saved in the `reviews_details` dir.

2. Revisions' Details Crawler:

> This script downloads the JSON files containing the details of every revision for the project specified in the settings. The revision JSON files are saved in the `revisions_details` dir.

3. Inline Comments Crawler:

> This script downloads the JSON files containing the inline comments of every revision for the project specified in the settings. The inline comments JSON files are saved in the `inline_comments_details` dir.

4. Discussion Writer:

> By using the data in the several JSON files about each review and revision, this script extracts the discussion of each revision and writes it in a separate text file for easy access. Each revision generates a discussion file which contains the description of that revision and the discussion/comments by the developers regarding the revision. The discussion files are saved in the `discussion` dir.

5. Check Parents:

> This script analyzes each revision of the project specified in the settings to save the commit ID of the parent for each specific revision on the Gerrit platform. The file containing the parent commit information is saved in a directory called `check_parents`.

6. Check Parents From Github:

> If the parent ID is not found in Script 5, this script analyzes each revision of the project specified in the settings to retrieve the parent commit ID for each specific revision, this time using the GitHub platform. The file containing the parent commit information is saved in a directory called `check_parents_github`.

7. Git Repo Populator:

> This script creates a git repository for the project specified in the settings. It automatically extracts and commit each snapshot in sequential order to the new git repository. The new git repository is located in the `git_repos` dir.<br><br>
In addition, the script creates a CSV file to store important data about each revision in a easily accessible way. In the CSV, each line represents a single revision, and the following information is saved in each column:<br><br>
**id**: an unique id to identify the revision within an specific community<br>
**review_number**: the unique review number in which the revision is part of<br>
**revision_number**: the number of the revision in the specific review<br>
**author**: the author of the revision<br>
**status**: the status of the revision<br>
**change_id**: the change id of this revision<br>
**url**: the URL in which one can access the web view of the revision<br>
**original\_before\_commit_id**: the commit id listed by gerrit as the version of the system before the revision took place<br>
**original\_after\_commit_id**: the commit id listed by gerrit as the version of the system after the revision took place<br>
**before\_commit\_id**: the commit id that represents the version of the system before the revision took place in the new git repository that was created by this script<br>
**after\_commit\_id**: the commit id that represents the version of the system after the revision took place in the new git repository that was created by this script

## Dataset

In `Update dataset` you can use the new datas.
obs: The `papyrus` project is still in progress.