import configparser
import glob
import re
import json
import os
import subprocess
import linecache
import sys
import traceback
import csv as my_csv
import requests
#obs1: Se o programa parar enquanto o pc esteja fazendo um commit (populating), pode ser que voce tenha que reajustar o cabeçalho no repositório git do projeto, além de alterar os saves, tanto do commit quanto do download no arquivo txt para voltar a ser escrito corretamente no arquivo metadata
#obs2: se a obs1 acontecer, dê o git reset --hard para o último commit feito no projeto na pasta git_repos
#obs3: existem commits de merge (com dois pais), nesses casos eu ignorei, fazendo com que alguns befores ficassem iguais ao after (script 5).
def compare_review_json(review_json_file_name):
    splitted_review_json_file_name = review_json_file_name.split("/")

    return int(splitted_review_json_file_name[len(splitted_review_json_file_name) - 1].split(".")[0])

# function to check whether both before and after snapshots of a certain revision have already been downloaded
def are_before_and_after_snapshots_downloaded(review_id, revision_number):
    if os.path.isfile("snapshots/" + PROJECT + "/before_" + review_id + "_rev" + revision_number + ".tar.gz") == False:
        return False
    elif os.path.isfile("snapshots/" + PROJECT + "/after_" + review_id + "_rev" + revision_number + ".tar.gz") == False:
        return False
    else:
        return True
def verifica_conexao_internet():
    try:
        response = requests.get("https://www.google.com", timeout=5)
        response.raise_for_status()
        return 1
    except requests.ConnectionError:
        return 0
# function to download a certain snapshot of a certain revision
# the 'when' parameter denotes whether the snapshot is before or after the revision
def download_snapshot(review_id, revision_number, commit_id, when):
    review_id_original = review_id
    revision_number_original=revision_number

    # the downloaded snapshot assumes a certain file name for different communities
    # see the settings file for more details
    if COMMUNITY == "Couchbase":
        snapshot_file_name = SNAPSHOT_FILE_NAME

    elif COMMUNITY == "Eclipse":
        snapshot_file_name = SNAPSHOT_FILE_NAME % (commit_id)

    # snapshots are stored following this naming pattern:
    # <review_id>_rev<revision_number>.tar.gz
    new_snapshot_file_name = "snapshots/" + PROJECT + "/" + when + "_" + review_id_original + "_rev" + revision_number_original + ".tar.gz"

    print("Downloading " + when + "_" + review_id + "_" + "rev" + revision_number + ".tar.gz of " + PROJECT)
    caminho_arquivo_csv = 'check_parents/' + PROJECT + ".csv"
    if COMMUNITY == "Couchbase" and when == "before":
        try:
            with open(caminho_arquivo_csv, mode='r', newline='') as arquivo_csv:
                leitor_csv = my_csv.reader(arquivo_csv)

                # Itere pelas linhas do CSV, onde cada linha é uma lista
                for linha in leitor_csv:
                    if len(linha) == 2 or (len(linha)==3 and linha[0] != "review_number" and linha[1]!="parent" and linha[0]!="Commits com pai/sem pai: " and linha[1] != ""):
                        check_review_number = linha[0].split("_")[0]
                        check_revision_number = linha[0].split("_")[1]
                        if check_review_number == review_id and check_revision_number == revision_number:
                            if linha[1] != "parent not found":
                                review_id = linha[1].split("_")[0]
                                revision_number = linha[1].split("_")[1]
                            if linha[1] == "parent not found":
                                with open("check_parents_github/" + PROJECT + ".csv", mode='r', newline='') as arquivo:
                                    leitor_github = my_csv.reader(arquivo)
                                    cabecalho_github = next(leitor_github)
                                    for linha_github in leitor_github:
                                        if len(linha_github)==2:
                                            if linha_github[0] == review_id+"_"+revision_number and linha_github[1]!="parent not found":
                                                snapshot_file_name_github = SNAPSHOT_FILE_NAME_GITHUB % (linha_github[1])
                                                snapshot_url_github = SNAPSHOT_URL_GITHUB % (PROJECT, linha_github[1])
                                                tgz_file_name = PROJECT + "-" + linha_github[1]
                                                # download the snapshot and move it to the correct directory
                                                subprocess.Popen(["wget", "-q", snapshot_url_github],
                                                                 stdout=subprocess.PIPE).communicate()
                                                subprocess.Popen(["unzip", snapshot_file_name_github],
                                                                 stdout=subprocess.PIPE).communicate()
                                                subprocess.Popen(["tar", "czvf", "tgz_file_name", "-C", tgz_file_name, "."], stdout=subprocess.PIPE).communicate()
                                                subprocess.Popen(["mv", "tgz_file_name", new_snapshot_file_name],
                                                                 stdout=subprocess.PIPE).communicate()
                                                os.remove(snapshot_file_name_github)
                                                subprocess.Popen(["rm", "-r", tgz_file_name], stdout=subprocess.PIPE).communicate()
                                                return
                            break
        except Exception as e:
            traceback.print_exc()
    # depending on the community, the snapshot_url is different
    # see the settings file for more details
    if COMMUNITY == "Couchbase":
        snapshot_url = SNAPSHOT_URL % (PROJECT, review_id, revision_number)
    elif COMMUNITY == "Eclipse":
        SNAPSHOT_PROJECT_1 = config['DETAILS']['snapshot_project_1']
        SNAPSHOT_PROJECT_2 = config['DETAILS']['snapshot_project_2']

        snapshot_url = SNAPSHOT_URL % (SNAPSHOT_PROJECT_1, SNAPSHOT_PROJECT_2, commit_id)
    # download the snapshot and move it to the correct directory
    subprocess.Popen(["wget", "-q", snapshot_url], stdout=subprocess.PIPE).communicate()
    subprocess.Popen(["mv", snapshot_file_name, new_snapshot_file_name], stdout=subprocess.PIPE).communicate()

    # throws an error when the snapshot cannot be downloaded
    if os.path.isfile(new_snapshot_file_name) == False:
        csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
        csv_file1.write(
            "Review Unable to Download ," + review_number + "," + revision_number + "\n")
        csv_file1.close()
        #verifica se há conexão com a internet
        if verifica_conexao_internet() == 0:
            sys.exit()
        raise ValueError("Unable to download " + new_snapshot_file_name)

    if is_snapshot_empty(new_snapshot_file_name) == True:
        raise ValueError("Empty snapshot " + new_snapshot_file_name)

def is_snapshot_empty(new_snapshot_file_name):
    snapshot_size = os.stat(new_snapshot_file_name).st_size

    if snapshot_size == 0:
        return True
    else:
        return False


def compare_review_json(review_json_file_name):
    splitted_review_json_file_name = review_json_file_name.split("/")

    return int(splitted_review_json_file_name[len(splitted_review_json_file_name) - 1].split(".")[0])


# function to check whether both before and after snapshots of a certain revision have already been downloaded
def are_before_and_after_snapshots_downloaded(review_id1, revision_number1):
    if os.path.isfile("snapshots/" + PROJECT + "/before_" + review_id1 + "_rev" + revision_number1 + ".tar.gz") == False:
        return False
    elif os.path.isfile("snapshots/" + PROJECT + "/after_" + review_id1 + "_rev" + revision_number1 + ".tar.gz") == False:
        return False
    else:
        return True


# function to read the csv file and convert it into a dictionary
def read_csv(csv_file_name):
    csv = {}
    headers = linecache.getline(csv_file_name, 1).split("\n")[0].split(",")

    for header in headers:
        csv[header] = []

    csv_file = open(csv_file_name, "r")
    csv_lines = csv_file.readlines()

    for index, line in enumerate(csv_lines):
        if index != 0:
            line = line.split("\n")[0]
            splitted_line = line.split(",")

            for value_index, value in enumerate(splitted_line):
                if value == "":
                    value = None

                csv[headers[value_index]].append(value)

    csv_file.close()

    return csv


# function to identify whether a certain before commit has already been added to the git repository
def get_before_commit_already_in_repo(original_before_commit_id, csv):
    for index, original_after_commit_id in enumerate(csv["original_after_commit_id"]):
        if original_after_commit_id == original_before_commit_id:
            return csv["after_commit_id"][index]

    return "None"


def populate_repo_with_revision_snapshot(revision_id, when):
    # extract the snapshot source code to the git repository
    extract_snapshot_to_repo(revision_id, when)

    # adds and/or deletes items given the changes in the git repository with the new snapshot
    prepare_repo_for_commit()

    commit_repo(revision_id, when)
    # identifies the latest commit id to be stored in the CSV
    commit_id = get_commit_id_from_last_commit()
    clean_repo()

    return commit_id


# function to extract the source code of the snapshot to the git repository
def extract_snapshot_to_repo(revision_id, when):
    # copies the snapshot file to the repository
    subprocess.Popen(
        ["cp", "snapshots/" + PROJECT + "/" + when + "_" + revision_id + ".tar.gz", "git_repos/" + PROJECT + "/"],
        stdout=subprocess.PIPE).communicate()

    # extracts the tar file and checks for errors in the extraction
    tar_feedback = str(subprocess.Popen(
        ["tar", "-xf", "git_repos/" + PROJECT + "/" + when + "_" + revision_id + ".tar.gz", "-C",
         "git_repos/" + PROJECT + "/"], stderr=subprocess.PIPE, stdout=subprocess.PIPE).communicate()[1])

    if "unexpected:" in tar_feedback or "Unexpected:" in tar_feedback or "error:" in tar_feedback or "Error:" in tar_feedback:
        raise ValueError("Error in extracting tar: " + when + "_" + revision_id)
    subprocess.Popen(["rm", "git_repos/" + PROJECT + "/" + when + "_" + revision_id + ".tar.gz"],
                     stdout=subprocess.PIPE).wait()

    # in the Couchbase community, the snapshot is extracted into an extra directory with the name <project>-<commit_id>
    # in this case, move all the content of this additional directory directly in the root of the repository


"""
    if COMMUNITY == "Couchbase":
        snapshot_folder = glob.glob("git_repos/" + PROJECT)[0]
        files_and_dirs = os.listdir(snapshot_folder)

        for item in files_and_dirs:
            if item != ".git":
                subprocess.Popen(["mv", snapshot_folder + "/" + item, "git_repos/" + PROJECT], stdout=subprocess.PIPE).wait()
        # removes the additional directory
        subprocess.Popen(["rm", "-rf", snapshot_folder], stdout=subprocess.PIPE).communicate()
"""


# removes the snapshot file
# function to add and/or remove items given the state of the git repository
def prepare_repo_for_commit():
    # the status message is used to identify the items that need to be added and/or removed
    git_status = str(
        subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "status"], stdout=subprocess.PIPE).communicate()[
            0])
    git_status = clean_status_message(git_status)
    changed_items = get_changed_items(git_status)
    untracked_items = get_untracked_items(git_status)
    # add/delete necessary items
    git_add_items(changed_items[0])
    git_add_items(untracked_items)
    git_delete_items(changed_items[1])


# function to format the status message in a line by line basis
def clean_status_message(git_status):
    cleaned_status = []
    git_status = git_status.split("\\n")
    for line in git_status:
        cleaned_status.append(line.replace("\\t", ""))
    return cleaned_status


# function to identify the items that were changed and deleted according to the status message
def get_changed_items(git_status):
    changed_line_flag = False
    changed_flag = False
    modified_items = []
    deleted_items = []
    inicio = 0
    fim = 0
    for line in git_status:
        splitted_line = line.split("  ")
        if "Arquivos " in line:
            for line2 in git_status[inicio + 2:]:
                if line2 == '':
                    L = git_status[inicio + 2:inicio + 2 + fim]
                    for itens in L:
                        modified_items.append(itens)
                fim = fim + 1
        if "No commits yet" in line:
            if "new" in splitted_line[0]:
                if splitted_line[1][0] == " ":
                    modified_items.append(splitted_line[1][1:])
                else:
                    modified_items.append(splitted_line[1])
        if changed_flag == False:
            if "Changes not staged" in line:
                changed_line_flag = True
                changed_flag = True
            if changed_line_flag == True and line == "":
                changed_flag = True
        elif changed_flag == True:
            if "modified:" in splitted_line[0]:
                if splitted_line[1][0] == " ":
                    modified_items.append(splitted_line[1][1:])
                else:
                    modified_items.append(splitted_line[1])
            elif "deleted:" in splitted_line[0]:
                deleted_items.append(splitted_line[2])
        inicio = inicio + 1
    return [modified_items, deleted_items]


# function to return the path of a file to be added or removed to the git repository
def get_item_path(splitted_line):
    splitted_line = splitted_line[1:len(splitted_line)]
    item_path = ""

    for item in splitted_line:
        if item != "":
            item_path = item_path + item + " "

    return item_path[0:len(item_path) - 1]


# function to identify the untracked items according to the status message
def get_untracked_items(git_status):
    untracked_line_flag = False
    untracked_flag = False
    untracked_items = []

    for line in git_status:
        if untracked_flag == False:
            if "Untracked files" in line:
                untracked_line_flag = True
            if untracked_line_flag == True and line == "":
                untracked_flag = True
        elif untracked_flag == True:
            if line == "":
                break
            else:
                untracked_items.append(line)

    return untracked_items


# function to add a list of items to the git repository
def git_add_items(items):
    for item in items:
        # add item and check for errors in the operation
        git_add_feedback = str(
            subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "add", item], stdout=subprocess.PIPE,
                             stderr=subprocess.PIPE).communicate())

        if "fatal:" in git_add_feedback[1]:
            raise ValueError("Error in adding item: " + item)


# function to delete a list of items to the git repository
def git_delete_items(items):
    for item in items:
        # delete item and check for errors in the operation
        git_delete_feedback = str(
            subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "rm", item], stdout=subprocess.PIPE,
                             stderr=subprocess.PIPE).communicate())

        if "fatal:" in git_delete_feedback[1]:
            raise ValueError("Error in deleting item: " + item)


# function to commit the current state of the git repository
def commit_repo(revision_id, when):
    a = subprocess.Popen(
        ["git", "-C", "git_repos/" + PROJECT + "/", "commit", "-m", "First commited as " + when + "_" + revision_id],
        stdout=subprocess.PIPE).communicate()


# function to identify the latest commit id in the git repository
def get_commit_id_from_last_commit():
    git_log = str(
        subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "log"], stdout=subprocess.PIPE).communicate()[0])
    git_log = git_log.split("\\n")
    ID = git_log[0].split(" ")[1]
    return ID


# function to clean the git repository
def clean_repo():
    diR = "git_repos/" + PROJECT
    files_and_dirs = os.listdir(diR)

    for item in files_and_dirs:
        if item != ".git":
            subprocess.Popen(["rm", "-rf", diR + "/" + item], stdout=subprocess.PIPE).wait()


def is_patch_valid(revision_id, before_commit_id, after_commit_id):
    result = True

    write_repo_patch(revision_id, before_commit_id, after_commit_id)
    write_gerrit_patch(revision_id)

    repo_patch_name = revision_id + "_repo.diff"
    gerrit_patch_name = revision_id + "_gerrit.diff"

    repo_patch_lines = get_patch_lines(repo_patch_name)
    gerrit_patch_lines = get_patch_lines(gerrit_patch_name)

    line_types = ["plus", "minus"]
    for line_type in line_types:
        if len(repo_patch_lines[line_type]) == len(gerrit_patch_lines[line_type]):
            if do_lines_match(repo_patch_lines[line_type], gerrit_patch_lines[line_type]) == False:
                result = False
                break
        else:
            result = False
            break

    # subprocess.Popen(["rm", repo_patch_name], stdout=subprocess.PIPE).communicate()[0]
    # subprocess.Popen(["rm", gerrit_patch_name], stdout=subprocess.PIPE).communicate()[0]

    return result


def write_repo_patch(revision_id, before_commit_id, after_commit_id):
    subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "diff", before_commit_id, after_commit_id],
                     stdout=open(revision_id + "_tmp.diff", "w")).communicate()
    tmp_patch = open(revision_id + "_tmp.diff", "r")
    repo_patch = open(revision_id + "_repo.diff", "w")

    for line in tmp_patch:
        if " @@ " in line:
            repo_patch.write(line[0:line.index(" @@ ") + 3] + "\n")
        else:
            repo_patch.write(line)

    repo_patch.close()

    subprocess.Popen(["rm", revision_id + "_tmp.diff"], stdout=subprocess.PIPE).communicate()[0]


def write_gerrit_patch(revision_id):
    downloaded_gerrit_patch = open("patches_details/" + PROJECT + "/" + revision_id + "_patch.diff", "r")
    gerrit_patch = open(revision_id + "_gerrit.diff", "w")
    write_line_flag = False

    for line in downloaded_gerrit_patch:
        if "diff --git " in line:
            write_line_flag = True

        if write_line_flag == True:
            if " @@ " in line:
                gerrit_patch.write(line[0:line.index(" @@ ") + 3] + "\n")
            else:
                gerrit_patch.write(line)

    gerrit_patch.close()


def get_patch_lines(patch_file_name):
    patch_file = open(patch_file_name, "r")
    patch_lines = {}
    plus = []
    minus = []
    normal = []

    for line in patch_file:
        # line = line.replace("\t"," ").replace("\n","")

        # if len(line) > 1:
        # if line[0] == "+" and line[1] == " ":
        # plus.append(line)
        # elif line[0] == "-" and line[1] == " ":
        # minus.append(line)
        # else:
        # normal.append(line)

        if line[0] == "+" and not "+++ " in line:
            plus.append(line)
        elif line[0] == "-" and not "--- " in line:
            minus.append(line)
        else:
            normal.append(line)

    patch_lines["plus"] = plus
    patch_lines["minus"] = minus
    patch_lines["normal"] = normal

    return patch_lines


def do_lines_match(repo_patch_lines, gerrit_patch_lines):
    print(repo_patch_lines[38])
    print(repo_patch_lines[39])
    print(repo_patch_lines[40])
    print(repo_patch_lines[41])
    print(repo_patch_lines[42])

    for line in repo_patch_lines:
        if not line in gerrit_patch_lines:
            # print(repo_patch_lines.index(line))
            # print(line)
            return False

    return True


def reset_repo():
    print("Reseting " + PROJECT + " repository")

    git_status = str(
        subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "status"], stdout=subprocess.PIPE).communicate()[
            0])
    git_status = clean_status_message(git_status)
    deleted_items = get_changed_items(git_status)[1]
    for item in deleted_items:
        # checkout item
        subprocess.Popen(["git", "-C", "git_repos/" + PROJECT + "/", "checkout", item], stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE).communicate()
def clear_snapshot():
    print("Deleting snapshots")
    path="snapshots/" + PROJECT + "/"
    filelist = glob.glob(os.path.join(path, "*"))
    for f in filelist:
        os.remove(f)
def write_save_snapshot(review_number,revision_number, revisions_downloads):
    review_number_save = open("Save_snapshots_number/" + PROJECT + ".txt", "w+")
    review_number_save.write(
        review_number + "\t" + revision_number + "\t" + str(revisions_downloads))
    review_number_save.close()
def write_save_commit(review_number1,revision_number1):
    review_commit_save = open("Save_commit_number/" + PROJECT + ".txt", "w+")
    review_commit_save.write(review_number1 + "\t" + revision_number1)
    review_commit_save.close()
def sort_metadata(PROJECT):
    csv_file_path = "metadata/" + PROJECT + ".csv"
    # Lista para armazenar os dados do CSV
    data = []

    # Ler o arquivo CSV
    with open(csv_file_path, 'r') as file:
        csv_reader = my_csv.reader(file)

        # Lê os dados do CSV (ignorando o cabeçalho)
        header = next(csv_reader)

        for row in csv_reader:
            data.append(row)

    def safe_int(s):
        try:
            return int(s)
        except (ValueError, TypeError):
            return float('inf')  # Define um valor "infinito" para representar uma string vazia

    # Ordenar os dados com base nos números na posição [1] e [2]
    data_sorted = sorted(data, key=lambda x: (safe_int(x[1]) if len(x) > 1 else float('inf'), safe_int(x[2]) if len(x) > 2 and x[2] else float('inf')))

    # Reescrever os dados ordenados no mesmo arquivo CSV
    with open(csv_file_path, 'w', newline='') as file:
        csv_writer = my_csv.writer(file)

        # Escrever o cabeçalho
        csv_writer.writerow(header)

        # Escrever os dados ordenados
        csv_writer.writerows(data_sorted)
######################### script starts here #################################

rg_id = re.compile("/(\d+)\.json")

config = configparser.ConfigParser()
#config.read("Couchbase_settings.ini")
config.read("Eclipse_settings.ini")

COMMUNITY = config['DETAILS']['community']
PROJECT = config['DETAILS']['project']
PROJECT_REVIEW_JSON = config['DETAILS']['project_review_json']
SNAPSHOT_URL = config['DETAILS']['snapshot_url']
if COMMUNITY == "Couchbase":
    SNAPSHOT_URL_GITHUB = config['DETAILS']['snapshot_url_github']
    SNAPSHOT_FILE_NAME_GITHUB = config['DETAILS']['snapshot_file_name_github']
SNAPSHOT_FILE_NAME = config['DETAILS']['snapshot_file_name']
REVISION_URL = config['DETAILS']['revision_url']
MAX_SNAPSHOT=int(config['DETAILS']['max_snapshot'])

if os.path.isdir("Save_snapshots_number/") == False:
    os.mkdir("Save_snapshots_number/")

if os.path.exists("Save_snapshots_number/" + PROJECT + ".txt") == False:
    review_number_save = open("Save_snapshots_number/" + PROJECT + ".txt", "w")
    review_number_save.write("0\t0\t0")
    review_number_save.close()
if os.path.isdir("Save_commit_number/") == False:
    os.mkdir("Save_commit_number/")
if os.path.exists("Save_commit_number/" + PROJECT + ".txt") == False:
    review_number_save = open("Save_commit_number/" + PROJECT + ".txt", "w")
    review_number_save.write("0\t0")
    review_number_save.close()

# create the snapshots directory if it does not exist
if os.path.isdir("snapshots") == False:
    os.mkdir("snapshots")

# create a directory for the project's snapshots if it does not exist
if os.path.isdir("snapshots/" + PROJECT) == False:
    os.mkdir("snapshots/" + PROJECT)
# get the reviews's details JSON files for the community sorted in ascending order
review_jsons = sorted(glob.glob("reviews_details/"+ COMMUNITY + "/*.json"), key=compare_review_json)

# create the git_repos directory if it does not exist
if os.path.isdir("git_repos") == False:
    os.mkdir("git_repos")
if os.path.isdir("metadata") == False:
    os.mkdir("metadata")
# create a git repo for the project if it does not exist
if os.path.isdir("git_repos/" + PROJECT) == False:
    subprocess.Popen(["git", "init", "git_repos/" + PROJECT], stdout=subprocess.PIPE).communicate()
# open the CSV file to store the revisions' data. Creates a new one if it does not exist
if os.path.isfile("metadata/" + PROJECT + ".csv") == False:
    csv_file = open("metadata/" + PROJECT + ".csv", "w")
    csv_file.write("id,review_number,revision_number,author,status,change_id,url,original_before_commit_id,original_after_commit_id,before_commit_id,after_commit_id\n")
    csv_file.close()
csv_file = open("metadata/" + PROJECT + ".csv", "a")
# read the existing CSV to avoid populating the repo with revisions that have already been added
csv = read_csv(csv_file.name)
revisions_already_in_repo = csv["id"]
csv_file.close()

review_number_check = open("Save_snapshots_number/" + PROJECT + ".txt", "r")
L=review_number_check.read().split("\t")
int_review_number_check = int(L[0])
int_revision_number_check = int(L[1])
revisions_downloads = int(L[2])
review_number_check.close()
review_commit_check = open("Save_commit_number/" + PROJECT + ".txt", "r")
L1=review_commit_check.read().split("\t")
int_review_commit = int(L1[0])
int_revision_commit = int(L1[1])
list_no_parents = []
list_no_parents_metadata=[]
if COMMUNITY == "Couchbase":
    with open("check_parents_github/" + PROJECT + ".csv", mode='r', newline='') as arquivo:
        leitor = my_csv.reader(arquivo)
        cabecalho = next(leitor)
        for linha in leitor:
            if len(linha) == 2:
                if linha[1] == "parent not found":
                    list_no_parents.append(linha[0].split("_")[0])
    list_no_parents = list(set(list_no_parents))
    with open("metadata/" + PROJECT + ".csv", mode='r', newline='') as arquivo_metadata:
        leitor_metadata = my_csv.reader(arquivo_metadata)
        cabecalho_metadata = next(leitor_metadata)
        for linha_metadata in leitor_metadata:
            if linha_metadata[0] == "Not Found parents from review: ":
                list_no_parents_metadata.append(linha_metadata[1])
    list_no_parents_metadata = list(set(list_no_parents_metadata))
#verificador = serve para alertar se o git está instalado ou não na sua máquina
#verificador1 = serve para verificar se a review em que a mineração parou possui mais de uma revision
#verificador2 = também serve para verificar se a review em que a mineração parou possui mais de uma revison, mas nesse caso é para caso a mineração tenha parado enquanto a população estava sendo feita
#verificador3 = serve para verificar se o json foi aberto ou para alertar se você baixou ou não o git
for review_json in review_jsons:
    try:
        review_number = rg_id.findall(review_json)[0]
        int_review = int(review_number)
        review_json = json.load(open(review_json))

    # check whether the review JSON is regarding the project
    # if yes, download the snapshots for before and after each revision
        if PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) not in list_no_parents):
            verificador1=False
            if int_review==int_review_number_check:
                verificador1=True #verifica se onde o script parou tem mais de uma revision
            if int_review>(int_review_number_check-1) and verificador1==False:
                # iterate over all revisions, sorted by the revision number
                for key, value in sorted(review_json["revisions"].items(), key = lambda revision_item : int(revision_item[1]["_number"])):
                    revision_number = str(value["_number"])
                # check if snapshots have already been downloaded. If yes, skip to next revision
                    if are_before_and_after_snapshots_downloaded(review_number, revision_number) == False:
                        revision_json_file_name = "revisions_details/" + PROJECT + "/" + review_number + "_rev" + revision_number + ".json"

                    # check if the s
                        # ,0´´´´´´´revision file exists
                        if os.path.isfile(revision_json_file_name) == True:
                            revision_json = json.load(open(revision_json_file_name))

                        # check if the revision has a parent
                        # Sometimes Gerrit does not record the parent of a certain revision. In these cases, the revision is ignored
                            if len(revision_json["parents"]) > 0:
                                before_revision_commit_id = revision_json["parents"][0]["commit"]
                                after_revision_commit_id = revision_json["commit"]
                            # the function download_snapshot may throw an exception when an error occurs in the download
                                #                             # in these cases print the error message and move to the next snapshot
                                try:
                                    download_snapshot(review_number, revision_number, before_revision_commit_id, "before")
                                    revisions_downloads=revisions_downloads+1
                                    download_snapshot(review_number, revision_number, after_revision_commit_id, "after")
                                    revisions_downloads=revisions_downloads+1
                                    write_save_snapshot(review_number, revision_number, revisions_downloads)
                                    if(revisions_downloads>=MAX_SNAPSHOT):
                                        csv_file = open("metadata/" + PROJECT + ".csv", "a")
                                        for review_json in review_jsons:
                                            verificador3 = False
                                            review_number1 = rg_id.findall(review_json)[0]
                                            int_review_number1 = int(review_number1)
                                            try:
                                                review_json = json.load(open(review_json))
                                                verificador3 = True
                                                # check whether the review JSON is regarding the project
                                                # if yes, populate the git repository with the snapshots for before and after each revision
                                                if PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) not in list_no_parents):
                                                    verificador2=False
                                                    if int_review_number1==int_review_commit:
                                                        verificador2=True
                                                    if int_review_number1>int_review_commit and verificador2==False:
                                                        # iterate over all revisions, sorted by the revision number
                                                        for key, value in sorted(review_json["revisions"].items(),
                                                                                 key=lambda revision_item: int(
                                                                                         revision_item[1]["_number"])):
                                                            revision_number1 = str(value["_number"])

                                                            # check whether both before and after snapshots were downloaded for the revision
                                                            if are_before_and_after_snapshots_downloaded(review_number1,
                                                                                                         revision_number1) == True:
                                                                revision_id = review_number1 + "_rev" + revision_number1

                                                                # only populate the repository if the revision has not been added before
                                                                if not revision_id in revisions_already_in_repo:
                                                                    print("Populating " + revision_id)

                                                                    revision_json = json.load(open(
                                                                        "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                                                    # collect additional data for the CSV
                                                                    # sometimes the author name has a comma. In these cases the comma is removed
                                                                    author = revision_json["author"]["name"].replace(",", "")
                                                                    status = review_json["status"]
                                                                    change_id = review_json["change_id"]
                                                                    revision_url = REVISION_URL % (review_number1, revision_number1)
                                                                    original_before_commit_id = revision_json["parents"][0][
                                                                        "commit"]
                                                                    original_after_commit_id = revision_json["commit"]

                                                                    # sometimes, revisions might share the same before commit
                                                                    # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                                                    before_commit_id = get_before_commit_already_in_repo(
                                                                        original_before_commit_id, csv)

                                                                    if before_commit_id == "None":
                                                                        before_commit_id = populate_repo_with_revision_snapshot(
                                                                            revision_id, "before")
                                                                    verificador = True
                                                                    after_commit_id = populate_repo_with_revision_snapshot(
                                                                        revision_id, "after")
                                                                    #print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                                                    csv_file.write(
                                                                        revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                                                    write_save_commit(review_number1,revision_number1)
                                                    elif int_review_number1==int_review_commit and verificador2==True:
                                                        for key, value in sorted(review_json["revisions"].items(),
                                                                                 key=lambda revision_item: int(
                                                                                     revision_item[1]["_number"])):
                                                            revision_number1 = str(value["_number"])
                                                            int_revision_number1 = int(review_number1)
                                                            if int_revision_number1>int_revision_commit:
                                                                # check whether both before and after snapshots were downloaded for the revision
                                                                if are_before_and_after_snapshots_downloaded(review_number1,
                                                                                                             revision_number1) == True:

                                                                    revision_id = review_number1 + "_rev" + revision_number1

                                                                    # only populate the repository if the revision has not been added before
                                                                    if not revision_id in revisions_already_in_repo:
                                                                        print("Populating " + revision_id)

                                                                        revision_json = json.load(open(
                                                                            "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                                                        # collect additional data for the CSV
                                                                        # sometimes the author name has a comma. In these cases the comma is removed
                                                                        author = revision_json["author"]["name"].replace(
                                                                            ",", "")
                                                                        status = review_json["status"]
                                                                        change_id = review_json["change_id"]
                                                                        revision_url = REVISION_URL % (
                                                                        review_number1, revision_number1)
                                                                        original_before_commit_id = \
                                                                        revision_json["parents"][0][
                                                                            "commit"]
                                                                        original_after_commit_id = revision_json["commit"]

                                                                        # sometimes, revisions might share the same before commit
                                                                        # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                                                        before_commit_id = get_before_commit_already_in_repo(
                                                                            original_before_commit_id, csv)

                                                                        if before_commit_id == "None":
                                                                            before_commit_id = populate_repo_with_revision_snapshot(
                                                                                revision_id, "before")
                                                                        verificador = True
                                                                        after_commit_id = populate_repo_with_revision_snapshot(
                                                                            revision_id, "after")
                                                                        # print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                                                        csv_file.write(
                                                                            revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                                                        write_save_commit(review_number1,revision_number1)
                                            except Exception as e:
                                                if verificador == False:
                                                    print(
                                                        "Git repository incorrectly initialized, please run the script again, if the error persists check if git is installed on your machine or if the repository is being created correctly")
                                                    sys.exit()
                                                elif verificador3 == True:
                                                    traceback.print_exc()
                                                    print("Error review_json not found: " + review_number1)
                                        revisions_downloads=0
                                        reset_repo()
                                        clear_snapshot()
                                        csv_file.close()
                                except ValueError as e:
                                    print(e)
                                    if verifica_conexao_internet() == 0:
                                        sys.exit()
                                    continue
                            elif len(revision_json["parents"]) == 0:
                                print("Json without parents:" + review_number + "/" + revision_number)
                                csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
                                csv_file1.write(
                                    "Not Found parents from review: ," + review_number + "," + revision_number + "\n")
                                csv_file1.close()
            elif int_review==int_review_number_check and verificador1==True:
                # iterate over all revisions, sorted by the revision number
                for key, value in sorted(review_json["revisions"].items(),
                                         key=lambda revision_item: int(revision_item[1]["_number"])):
                    revision_number = str(value["_number"])
                    int_revision = int(revision_number)
                    if int_revision>int_revision_number_check:
                        # check if snapshots have already been downloaded. If yes, skip to next revision
                        if are_before_and_after_snapshots_downloaded(review_number, revision_number) == False:
                            revision_json_file_name = "revisions_details/" + PROJECT + "/" + review_number + "_rev" + revision_number + ".json"

                            # check if the revision file exists
                            if os.path.isfile(revision_json_file_name) == True:
                                revision_json = json.load(open(revision_json_file_name))

                                # check if the revision has a parent
                                # Sometimes Gerrit does not record the parent of a certain revision. In these cases, the revision is ignored
                                if len(revision_json["parents"]) > 0:
                                    before_revision_commit_id = revision_json["parents"][0]["commit"]
                                    after_revision_commit_id = revision_json["commit"]

                                    # the function download_snapshot may throw an exception when an error occurs in the download
                                    # in these cases print the error message and move to the next snapshot
                                    try:
                                        download_snapshot(review_number, revision_number, before_revision_commit_id,
                                                          "before")
                                        revisions_downloads = revisions_downloads + 1
                                        download_snapshot(review_number, revision_number, after_revision_commit_id, "after")
                                        revisions_downloads = revisions_downloads + 1
                                        write_save_snapshot(review_number,revision_number, revisions_downloads)
                                        if(revisions_downloads>=MAX_SNAPSHOT):
                                            csv_file = open("metadata/" + PROJECT + ".csv", "a")
                                            for review_json in review_jsons:
                                                review_number1 = rg_id.findall(review_json)[0]
                                                int_review_number1 = int(review_number1)
                                                try:
                                                    review_json = json.load(open(review_json))

                                                    # check whether the review JSON is regarding the project
                                                    # if yes, populate the git repository with the snapshots for before and after each revision
                                                    if PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) not in list_no_parents):
                                                        verificador2 = False
                                                        if int_review_number1 == int_review_commit:
                                                            #verifica em qual review a minerção foi parada
                                                            verificador2 = True
                                                        if int_review_number1 > int_review_commit and verificador2 == False:
                                                            # iterate over all revisions, sorted by the revision number
                                                            for key, value in sorted(review_json["revisions"].items(),
                                                                                     key=lambda revision_item: int(
                                                                                         revision_item[1]["_number"])):
                                                                revision_number1 = str(value["_number"])

                                                                # check whether both before and after snapshots were downloaded for the revision
                                                                if are_before_and_after_snapshots_downloaded(review_number1,
                                                                                                             revision_number1) == True:
                                                                    revision_id = review_number1 + "_rev" + revision_number1

                                                                    # only populate the repository if the revision has not been added before
                                                                    if not revision_id in revisions_already_in_repo:
                                                                        print("Populating " + revision_id)

                                                                        revision_json = json.load(open(
                                                                            "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                                                        # collect additional data for the CSV
                                                                        # sometimes the author name has a comma. In these cases the comma is removed
                                                                        author = revision_json["author"]["name"].replace(",",
                                                                                                                         "")
                                                                        status = review_json["status"]
                                                                        change_id = review_json["change_id"]
                                                                        revision_url = REVISION_URL % (
                                                                        review_number1, revision_number1)
                                                                        original_before_commit_id = revision_json["parents"][0][
                                                                            "commit"]
                                                                        original_after_commit_id = revision_json["commit"]

                                                                        # sometimes, revisions might share the same before commit
                                                                        # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                                                        before_commit_id = get_before_commit_already_in_repo(
                                                                            original_before_commit_id, csv)

                                                                        if before_commit_id == "None":
                                                                            before_commit_id = populate_repo_with_revision_snapshot(
                                                                                revision_id, "before")
                                                                        verificador = True
                                                                        after_commit_id = populate_repo_with_revision_snapshot(
                                                                            revision_id, "after")
                                                                        # print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                                                        csv_file.write(
                                                                            revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                                                        write_save_commit(review_number1,revision_number1)
                                                        elif int_review_number1 == int_review_commit and verificador2 == True:
                                                            for key, value in sorted(review_json["revisions"].items(),
                                                                                     key=lambda revision_item: int(
                                                                                         revision_item[1]["_number"])):
                                                                revision_number1 = str(value["_number"])
                                                                int_revision_number1 = int(review_number1)
                                                                if int_revision_number1 > int_revision_commit:
                                                                    # check whether both before and after snapshots were downloaded for the revision
                                                                    if are_before_and_after_snapshots_downloaded(review_number1,
                                                                                                                 revision_number1) == True:
                                                                        revision_id = review_number1 + "_rev" + revision_number1

                                                                        # only populate the repository if the revision has not been added before
                                                                        if not revision_id in revisions_already_in_repo:
                                                                            print("Populating " + revision_id)

                                                                            revision_json = json.load(open(
                                                                                "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                                                            # collect additional data for the CSV
                                                                            # sometimes the author name has a comma. In these cases the comma is removed
                                                                            author = revision_json["author"]["name"].replace(
                                                                                ",", "")
                                                                            status = review_json["status"]
                                                                            change_id = review_json["change_id"]
                                                                            revision_url = REVISION_URL % (
                                                                                review_number1, revision_number1)
                                                                            original_before_commit_id = \
                                                                                revision_json["parents"][0][
                                                                                    "commit"]
                                                                            original_after_commit_id = revision_json["commit"]

                                                                            # sometimes, revisions might share the same before commit
                                                                            # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                                                            before_commit_id = get_before_commit_already_in_repo(
                                                                                original_before_commit_id, csv)

                                                                            if before_commit_id == "None":
                                                                                before_commit_id = populate_repo_with_revision_snapshot(
                                                                                    revision_id, "before")
                                                                            verificador = True
                                                                            after_commit_id = populate_repo_with_revision_snapshot(
                                                                                revision_id, "after")
                                                                            # print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                                                            csv_file.write(
                                                                                revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                                                            write_save_commit(review_number1,revision_number1)
                                                except Exception as e:
                                                    #alerta do git que precisa estar instalado na sua máquina
                                                    if verificador == False:
                                                        print(
                                                            "Git repository incorrectly initialized, please run the script again, if the error persists check if git is installed on your machine or if the repository is being created correctly")
                                                        sys.exit()
                                                    else:
                                                        traceback.print_exc()
                                                        print("Error review_json not found: " + review_number1)
                                            revisions_downloads = 0
                                            reset_repo()
                                            clear_snapshot()
                                            csv_file.close()
                                    except ValueError as e:
                                        print(e)
                                        if verifica_conexao_internet() == 0:
                                            sys.exit()
                                        continue
                                elif len(revision_json["parents"]) == 0:
                                    print("Json without parents:" + review_number + "/" + revision_number)
                                    csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
                                    csv_file1.write("Not Found parents from review: ," + review_number + "," + revision_number + "\n")
                                    csv_file1.close()
        elif PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) in list_no_parents) and (str(review_json["_number"]) not in list_no_parents_metadata):
            print("Json without parents:" + review_number)
            csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
            csv_file1.write("Not Found parents from review: ," + review_number + "\n")
            csv_file1.close()
            list_no_parents.remove(str(review_json["_number"]))
#no Eclipse,
#é necessário alterar o project, project_review_json, snapshot_project1 e 2 para trocar de projeto
    except:
        print("Error review_json not found: " + review_number)
        csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
        csv_file1.write("Not Found review: ," + review_number + "\n")
        csv_file1.close()
        continue
if revisions_downloads>0:
    csv_file = open("metadata/" + PROJECT + ".csv", "a")
    for review_json in review_jsons:
        review_number1 = rg_id.findall(review_json)[0]
        int_review_number1 = int(review_number1)
        try:
            review_json = json.load(open(review_json))

            # check whether the review JSON is regarding the project
            # if yes, populate the git repository with the snapshots for before and after each revision
            if PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) not in list_no_parents):
                verificador2 = False
                if int_review_number1 == int_review_commit:
                    verificador2 = True
                if int_review_number1 > int_review_commit and verificador2 == False:
                    # iterate over all revisions, sorted by the revision number
                    for key, value in sorted(review_json["revisions"].items(),
                                             key=lambda revision_item: int(
                                                 revision_item[1]["_number"])):
                        revision_number1 = str(value["_number"])

                        # check whether both before and after snapshots were downloaded for the revision
                        if are_before_and_after_snapshots_downloaded(review_number1,
                                                                     revision_number1) == True:
                            revision_id = review_number1 + "_rev" + revision_number1

                            # only populate the repository if the revision has not been added before
                            if not revision_id in revisions_already_in_repo:
                                print("Populating " + revision_id)

                                revision_json = json.load(open(
                                    "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                # collect additional data for the CSV
                                # sometimes the author name has a comma. In these cases the comma is removed
                                author = revision_json["author"]["name"].replace(",", "")
                                status = review_json["status"]
                                change_id = review_json["change_id"]
                                revision_url = REVISION_URL % (review_number1, revision_number1)
                                original_before_commit_id = revision_json["parents"][0][
                                    "commit"]
                                original_after_commit_id = revision_json["commit"]

                                # sometimes, revisions might share the same before commit
                                # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                before_commit_id = get_before_commit_already_in_repo(
                                    original_before_commit_id, csv)

                                if before_commit_id == "None":
                                    before_commit_id = populate_repo_with_revision_snapshot(
                                        revision_id, "before")
                                verificador = True
                                after_commit_id = populate_repo_with_revision_snapshot(
                                    revision_id, "after")
                                # print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                csv_file.write(
                                    revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                write_save_commit(review_number1,revision_number1)
                elif int_review_number1 == int_review_commit and verificador2 == True:
                    for key, value in sorted(review_json["revisions"].items(),
                                             key=lambda revision_item: int(
                                                 revision_item[1]["_number"])):
                        revision_number1 = str(value["_number"])
                        int_revision_number1 = int(review_number1)
                        if int_revision_number1 > int_revision_commit:
                            # check whether both before and after snapshots were downloaded for the revision
                            if are_before_and_after_snapshots_downloaded(review_number1,
                                                                         revision_number1) == True:
                                revision_id = review_number1 + "_rev" + revision_number1

                                # only populate the repository if the revision has not been added before
                                if not revision_id in revisions_already_in_repo:
                                    print("Populating " + revision_id)

                                    revision_json = json.load(open(
                                        "revisions_details/" + PROJECT + "/" + revision_id + ".json"))

                                    # collect additional data for the CSV
                                    # sometimes the author name has a comma. In these cases the comma is removed
                                    author = revision_json["author"]["name"].replace(
                                        ",", "")
                                    status = review_json["status"]
                                    change_id = review_json["change_id"]
                                    revision_url = REVISION_URL % (
                                        review_number1, revision_number1)
                                    original_before_commit_id = \
                                        revision_json["parents"][0][
                                            "commit"]
                                    original_after_commit_id = revision_json["commit"]

                                    # sometimes, revisions might share the same before commit
                                    # in these cases, it does not adds the same before commit to the repository, but rather uses the one that have already been added
                                    before_commit_id = get_before_commit_already_in_repo(
                                        original_before_commit_id, csv)

                                    if before_commit_id == "None":
                                        before_commit_id = populate_repo_with_revision_snapshot(
                                            revision_id, "before")
                                    verificador = True
                                    after_commit_id = populate_repo_with_revision_snapshot(
                                        revision_id, "after")
                                    # print(revision_id, review_number, revision_number, author, status, change_id, revision_url, original_before_commit_id, original_after_commit_id, before_commit_id, after_commit_id)
                                    csv_file.write(
                                        revision_id + "," + review_number1 + "," + revision_number1 + "," + author + "," + status + "," + change_id + "," + revision_url + "," + original_before_commit_id + "," + original_after_commit_id + "," + before_commit_id + "," + after_commit_id + "\n")
                                    write_save_commit(review_number1,revision_number1)
            elif PROJECT_REVIEW_JSON == review_json["project"] and (str(review_json["_number"]) in list_no_parents) and (str(review_json["_number"]) not in list_no_parents_metadata):
                print("Json without parents:" + review_number1)
                csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
                csv_file1.write("Not Found parents from review: ," + review_number1 + "\n")
                csv_file1.close()
                list_no_parents.remove(str(review_json["_number"]))
        except Exception as e:
            if verificador1 == False:
                print(
                    "Git repository incorrectly initialized, please run the script again, if the error persists check if git is installed on your machine or if the repository is being created correctly")
                sys.exit()
            else:
                print("Error review_json not found: " + review_number1)
                csv_file1 = open("metadata/" + PROJECT + ".csv", "a")
                csv_file1.write("Not Found review: ," + review_number1 + "\n")
                csv_file1.close()
    revisions_downloads = 0
    reset_repo()
    clear_snapshot()
    csv_file.close()
sort_metadata(PROJECT)
