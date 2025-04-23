import configparser
import csv
import glob
import json
import os

import requests

#script usado apenas para Couchbase
def compare_revision_json(revision_json_file_name):
    splitted_revision_json_file_name = revision_json_file_name.split("/")

    return int(splitted_revision_json_file_name[len(splitted_revision_json_file_name) - 1].split(".")[0].split("_")[0])

def take_id_revision(revision_number):
    for revision_number_json in revisions_jsons:
        revision_number_json_splitted = revision_number_json.split("/")[2].split(".")[0].replace("rev","")
        if revision_number_json_splitted == revision_number:
            json_file = json.load(open(revision_number_json))
            commit_parents = json_file["parents"]
            return commit_parents


def check_URL_in_github(PROJECT,commit_parents, revision_number):
    if commit_parents==[]:
        check_csv_github = open("check_parents_github/" + PROJECT + ".csv", "a")
        check_csv_github.write(revision_number + "," + "parent not found" + "\n")
        check_csv_github.close()
    else:
        id_revision = commit_parents[0]["commit"]
        url = commit_url_github % (PROJECT, id_revision)
        try:
            response = requests.get(url)
            if response.status_code == 200:
                print(f"A página {url} está acessível.")
                csv_file = open("check_parents_github/" + PROJECT + ".csv", "a")
                csv_file.write(revision_number + "," + id_revision + "\n")
                csv_file.close()
            else:
                print(f"A página {url} retornou um código de status {response.status_code}.")
                check_csv_github = open("check_parents_github/" + PROJECT + ".csv", "a")
                check_csv_github.write(revision_number + "," + "parent not found" + "\n")
                check_csv_github.close()
        except requests.exceptions.RequestException as e:
            print(f"Ocorreu um erro ao acessar a página {url}: {e}")

#scripts starts here
config = configparser.ConfigParser()
config.read("Couchbase_settings.ini")
#config.read("Eclipse_settings.ini")
COMMUNITY = config['DETAILS']['community']
PROJECT = config['DETAILS']['project']
PROJECT_REVIEW_JSON = config['DETAILS']['project_review_json']
revisions_jsons = sorted(glob.glob("revisions_details/"+ PROJECT + "/*.json"),key=compare_revision_json)
commit_url_github = config['DETAILS']['commit_url_github']
if os.path.isdir("check_parents_github") == False:
    os.mkdir("check_parents_github")

if os.path.isfile("check_parents_github/" + PROJECT + ".csv") == False:
    csv_file = open("check_parents_github/" + PROJECT + ".csv", "w")
    csv_file.write("review_number,parent\n")
    csv_file.close()

with open('check_parents/'+PROJECT+".csv", mode='r', newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)

    # Para ler a primeira linha do arquivo (cabeçalho), use next
    cabecalho = next(leitor_csv)
    print("Cabeçalho:", cabecalho)

    # Para ler a próxima linha do arquivo (primeira linha de dados), use next novamente
    #commits_sem_pai=[]
    for linha in leitor_csv:
        if len(linha)==2 or (len(linha)==3 and linha[0] != "review_number" and linha[1]!="parent" and linha[0]!="Commits com pai/sem pai: " and linha[1] != ""):
            if linha[1]=="parent not found":
                commit_parents = take_id_revision(linha[0])
                check_URL_in_github(PROJECT, commit_parents, linha[0])
    """
    lista_sem_duplicatas = sorted(list(set(commits_sem_pai)),key = compare_revision_json)
    for revision in lista_sem_duplicatas:
        commit_parents = take_id_revision(revision)
        check_URL_in_github(PROJECT,commit_parents,revision)
    """

with open("check_parents_github/" + PROJECT + ".csv", mode = 'r', newline='') as arquivo:
    leitor = csv.reader(arquivo)
    cabecalho = next(leitor)
    comPai=0
    semPai=0
    for linha in leitor:
        if len(linha)==2:

            if linha[1] == "parent not found":
                semPai+=1
            elif linha[0]!="Commits com pai/sem pai: ":
                comPai+=1
csv_file = open("check_parents_github/" + PROJECT + ".csv", "a")
csv_file.write("Commits com pai/sem pai: " + "," + str(comPai) + "," + str(semPai) + "\n")
csv_file.close()
#OBS: se na revision tiver dois pais, e no gerrit o pai estar presente em somente um deles, o github
#verifica para ambos pais, isso faz com que no github tenham algumas linhas a mais (essa questão do
#commit ter masi de um pai ainda deve ser melhor tratada)