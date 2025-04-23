
import configparser
import glob
import json
import os
import re
import socket
import csv
import traceback

#script usado apenas para Couchbase
def compare_revision_json(revision_json_file_name):
    splitted_revision_json_file_name = revision_json_file_name.split("/")

    return int(splitted_revision_json_file_name[len(splitted_revision_json_file_name) - 1].split(".")[0].split("_")[0])

def write_csv(review_number, revision_number, comparing_review_number, comparing_revision_number):
    csv_file = open("check_parents/" + PROJECT + ".csv", "a")
    csv_file.write(review_number + "_" + revision_number + "," + comparing_review_number + "_" + comparing_revision_number + "\n")
    csv_file.close()
rg_id = re.compile("/(\d+)\.json")
def compare_review_json(review_json_file_name):
    splitted_review_json_file_name = review_json_file_name.split("/")

    return int(splitted_review_json_file_name[len(splitted_review_json_file_name) - 1].split(".")[0])

#script starts here
socket.setdefaulttimeout(50)
config = configparser.ConfigParser()
config.read("Couchbase_settings.ini")
#config.read("Eclipse_settings.ini")
COMMUNITY = config['DETAILS']['community']
PROJECT = config['DETAILS']['project']
PROJECT_REVIEW_JSON = config['DETAILS']['project_review_json']
revisions_jsons = sorted(glob.glob("revisions_details/"+ PROJECT + "/*.json"),key=compare_revision_json)
review_jsons = sorted(glob.glob("reviews_details/"+ COMMUNITY + "/*.json"), key=compare_review_json)
if os.path.isdir("check_parents") == False:
    os.mkdir("check_parents")

if os.path.isfile("check_parents/" + PROJECT + ".csv") == False:
    csv_file = open("check_parents/" + PROJECT + ".csv", "w")
    csv_file.write("review_number,parent\n")
    csv_file.close()

for revision_json in revisions_jsons:
    json_file = json.load(open(revision_json))
    #print(json_file)
    splitted_review_number = revision_json.split("/")[2]
    review_number = splitted_review_number.split("_")[0]
    revision_number = splitted_review_number.split("_")[1].replace("rev","").split(".")[0]
    #print(review_number,revision_number)
    commit_original = json_file["commit"]
    #print(commit_original)
    commit_parents = json_file["parents"]
    if commit_parents==[]:
        check_csv_github = open("check_parents/" + PROJECT + ".csv", "a")
        check_csv_github.write(review_number + "_" + revision_number + "," + "parent not found" + "\n")
        check_csv_github.close()
    #print(commit_parents)
    number_parents = len(commit_parents)
    if len(commit_parents)==1:
        verificador = False
        commit_parent = commit_parents[0]["commit"]
        #print(commit_parent)
        for comparing_revision in revisions_jsons:
            comparing_json = json.load(open(comparing_revision))
            comparing_splitted_review_number = comparing_revision.split("/")[2]
            comparing_review_number = comparing_splitted_review_number.split("_")[0]
            comparing_revision_number = comparing_splitted_review_number.split("_")[1].replace("rev", "").split(".")[0]
            #print(comparing_review_number,comparing_revision_number)
            comparing_commit_original = comparing_json["commit"]
            #print(comparing_commit_original)
            #print(commit_original, comparing_commit_original, commit_parent)
            if comparing_commit_original == commit_parent:
                write_csv(review_number,revision_number,comparing_review_number,comparing_revision_number)
                print(f'Encontrado commit pai para revisão {review_number}, revisão {revision_number}')
                verificador = True
                break
        if verificador==False:
            print(f'Não foi encontrado commit pai para revisão {review_number}, revisão {revision_number}')
            csv_file = open("check_parents/" + PROJECT + ".csv", "a")
            csv_file.write(review_number + "_" + revision_number + "," + "parent not found" + "\n")
            csv_file.close()
# Somando quantos commits tem pai e não tem
# Abra o arquivo CSV para leitura
with open('check_parents/'+PROJECT+".csv", mode='r', newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)

    # Para ler a primeira linha do arquivo (cabeçalho), use next
    cabecalho = next(leitor_csv)
    print("Cabeçalho:", cabecalho)

    # Para ler a próxima linha do arquivo (primeira linha de dados), use next novamente
    comPai=0
    semPai=0
    for linha in leitor_csv:
        if len(linha)==2 or (len(linha)==3 and linha[0] != "review_number" and linha[1]!="parent" and linha[0]!="Commits com pai/sem pai: " and linha[1] != ""):
            if linha[1]=="parent not found":
                semPai=semPai+1
            else:
                comPai = comPai+1
    print(comPai)
    print(semPai)
csv_file = open("check_parents/" + PROJECT + ".csv", "a")
csv_file.write("Commits com pai/sem pai: " + "," + str(comPai) + "," + str(semPai) + "\n")
csv_file.close()
"""
#checando as quantidades de reviews afetadas
with open('check_parents/'+PROJECT+".csv", mode='r', newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)

    # Para ler a primeira linha do arquivo (cabeçalho), use next
    cabecalho = next(leitor_csv)
    print("Cabeçalho:", cabecalho)

    # Para ler a próxima linha do arquivo (primeira linha de dados), use next novamente
    LsemPai=[]
    LcomPai=[]
    for linha in leitor_csv:
        if len(linha) == 2:
            if linha[1]=="parent not found":
                if linha[0].split("_")[0] in LsemPai:
                    continue
                else:
                    LsemPai.append(linha[0].split("_")[0])
            else:
                if linha[0].split("_")[0] in LcomPai:
                    continue
                else:
                    LcomPai.append(linha[0].split("_")[0])
    for i, reviews in enumerate(LcomPai):
        for reviewsSemPai in LsemPai:
            if reviewsSemPai == reviews:
                LcomPai[i] = " "
    totalLcomPai=0
    for reviews in LcomPai:
        if reviews==" ":
            continue
        else:
            totalLcomPai+=1
    print(LcomPai)
    print("=================================================")
    print(LsemPai)
    print("=================================================")
    print(totalLcomPai)
    print(len(LsemPai))
    print(totalLcomPai + len(LsemPai))
    total_reviews = 0
    for review_json in review_jsons:
        review_number = rg_id.findall(review_json)[0]
        json_file = json.load(open(review_json))
        if PROJECT_REVIEW_JSON == json_file["project"]:
            total_reviews = total_reviews+1
    print(total_reviews)
"""